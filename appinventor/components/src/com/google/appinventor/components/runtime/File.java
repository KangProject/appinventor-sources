package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FileUtil;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

/**
 * An interface for working with files and directories on the phone.
 *
 */
@DesignerComponent(version = YaVersion.FILE_COMPONENT_VERSION,
    description = "Non-visible component for manipulating files on the phone.",
    category = ComponentCategory.STORAGE,
    nonVisible = true,
    iconName = "images/file.png")
@SimpleObject
@UsesPermissions(permissionNames =
                 "android.permission.READ_EXTERNAL_STORAGE," +
                 "android.permission.WRITE_EXTERNAL_STORAGE")
public class File extends AndroidNonvisibleComponent implements Component {
                
  /**
   * Creates a new File component.
   *
   * @param container the Form that this component is contained in.
   */
  public File(ComponentContainer container) {
    super(container.$form());
    final Context context = (Context) container.$context();
  }
  
  /**
   * Stores the text to a specified file on the phone.
   *
   * @param text the text to be stored
   * @param fileName the file to which the text will be stored
   */
  @SimpleFunction
  public void OverwriteFile(String text, String fileName) {
      FileUtil.checkExternalStorageWriteable();
      Write(fileName, text, false);
  }
  
  /**
   * Appends text to a specified file on the phone.
   *
   * @param text the text to be stored
   * @param fileName the file to which the text will be stored
   */
  @SimpleFunction
  public void AppendToFile(String text, String fileName) {
            FileUtil.checkExternalStorageWriteable();
      Write(fileName, text, true);
  }

  /**
   * Retrieve the text stored in a specified file.
   *
   * @param fileName the file from which the text is read
   * @return the text stored in the specified file
   * @throws FileNotFoundException if the file cannot be found
   * @throws IOException if the text cannot be read from the file
   */
  @SimpleFunction
  public String ReadFrom(String fileName) {
      String filepath = AbsoluteFileName(fileName);
      java.io.File file = new java.io.File(filepath);
      StringBuilder sb = new StringBuilder();
      try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file)); 
                  String line;
                  while ((line = bufferedReader.readLine()) != null) {
                          sb.append(line);
                          sb.append(System.getProperty("line.separator"));
              }
          } catch (FileNotFoundException e) {
                        form.dispatchErrorOccurredEvent(this, "ReadFrom",
                                  ErrorMessages.ERROR_UNABLE_TO_LOAD_MEDIA, filepath);
          } catch (IOException e) {
                  form.dispatchErrorOccurredEvent(this, "ReadFrom",
                                  ErrorMessages.ERROR_UNABLE_TO_LOAD_MEDIA, filepath);
      }
      return sb.toString();
  }
  
  /**
   * Delete the specified file.
   * 
   * @param fileName the file to be deleted
   */
  @SimpleFunction
  public void Delete(String fileName) {
          String filepath = AbsoluteFileName(fileName);
          java.io.File file = new java.io.File(filepath);
          file.delete();
  }
  
  /**
   * Writes to the specified file.
   * 
   * @param fileName the file to write 
   * @param text to write to the file
   * @param append determines whether text should be appended to the file, or overwrite the file
   */
  private void Write(String filename, String text, boolean append) {
      String filepath = AbsoluteFileName(filename);
            java.io.File file = new java.io.File(filepath);
      if(!file.exists()){
              try {
                      file.createNewFile();
        } catch (IOException e) {
                  form.dispatchErrorOccurredEvent(this, "AppendTo",
                                  ErrorMessages.ERROR_UNABLE_TO_LOAD_MEDIA, filepath);
        }
      }
      
      try {
              FileOutputStream fileWriter = new FileOutputStream(file, append);
                  OutputStreamWriter out = new OutputStreamWriter(fileWriter);
                  out.write(text);
                  out.flush();
                    out.close();
                    fileWriter.close();
          }
          catch (IOException e) {
                  form.dispatchErrorOccurredEvent(this, "AppendTo",
                                  ErrorMessages.ERROR_UNABLE_TO_LOAD_MEDIA, filepath);
      }
  }
  
  /**
   * Returns absolute file path. By default, returns file path to the assets folder of AppInventor.
   * 
   * @param fileName the file used to construct the file path 
   */
  private String AbsoluteFileName(String filename) {
          if (filename.startsWith("//")) return filename;
          else if (filename.startsWith("/")) return Environment.getExternalStorageDirectory().getPath() + filename;                  
          else return Environment.getExternalStorageDirectory().getPath() + "/AppInventor/assets/" + filename;
  }
}
