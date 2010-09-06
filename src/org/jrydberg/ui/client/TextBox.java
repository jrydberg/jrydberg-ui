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

/**
 * @author jrydberg
 *
 */
public class TextBox extends com.google.gwt.user.client.ui.TextBox {

  public interface Css extends CssResource {
    String textBox();
    
  }
  
  public interface Resources extends ClientBundle {
    Resources INSTANCE = (Resources) GWT.create(Resources.class);
     
    @Source("resources/TextBox.css")
    Css textBoxCss();
  }
   
  protected final Resources resources;
  
  public TextBox() {
    this(Resources.INSTANCE);
  }

  public TextBox(Resources resources) {
    this.resources = resources;
    resources.textBoxCss().ensureInjected();
    setStyleName(resources.textBoxCss().textBox());
  }
  
}
