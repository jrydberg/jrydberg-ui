/**
 * Copyright 2010 Johan Rydberg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jrydberg.ui.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

/**
 * @author jrydberg
 *
 */
public class Button extends com.google.gwt.user.client.ui.Button {


  public interface Css extends CssResource {
    String button();
    
  }
  
  public interface Resources extends ClientBundle {
    Resources INSTANCE = (Resources) GWT.create(Resources.class);
     
    @Source("resources/Button.css")
    Css buttonCss();
  }
   
  protected final Resources resources;
  
  public Button() {
    this(Resources.INSTANCE);
  }
  
  public Button(ImageResource imageResource) {
    this(new Image(imageResource));
  }

  public Button(Resources resources, ImageResource imageResource) {
    this(resources, new Image(imageResource));
  }
  
  public Button(Image image) {
    this(Resources.INSTANCE, image);
  }
  
  public Button(String html) {
    this(Resources.INSTANCE, html);
  }

  public Button(Resources resources) {
    this.resources = resources;
    resources.buttonCss().ensureInjected();
    setStyleName(resources.buttonCss().button());
  }

  public Button(Resources resources, String html) {
    super(html);
    this.resources = resources;
    resources.buttonCss().ensureInjected();
    setStyleName(resources.buttonCss().button());
  }

  public Button(Resources resources, Image image) {
    this(resources);
    getElement().appendChild(image.getElement());
  }

}
