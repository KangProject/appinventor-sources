// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the MIT License https://raw.github.com/mit-cml/app-inventor/master/mitlicense.txt

package com.google.appinventor.client.editor.youngandroid.properties;

import static com.google.appinventor.client.Ode.MESSAGES;
import com.google.appinventor.client.widgets.properties.ChoicePropertyEditor;

/**
 * Property editor for app theme.
 *
 */
 public class YoungAndroidThemeChoicePropertyEditor extends ChoicePropertyEditor {

  // Theme choices
  private static final Choice[] themes = new Choice[] {
    new Choice(MESSAGES.holoDarkTheme(), "Theme.Holo.NoActionBar"),
    new Choice(MESSAGES.holoDarkFullscreenTheme(), "Theme.Holo.NoActionBar.Fullscreen"),
    new Choice(MESSAGES.holoLightTheme() , "Theme.Holo.Light.NoActionBar"),
    new Choice(MESSAGES.holoLightFullscreenTheme(), "Theme.Holo.Light.NoActionBar.Fullscreen"),
  };

  public YoungAndroidThemeChoicePropertyEditor() {
    super(themes);
  }
}
