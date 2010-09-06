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
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author jrydberg
 *
 */
public class ButtonBar extends FlowPanel  {

  public interface Css extends CssResource {
    String buttonBar();
    
    String button();
    
    String left();
    
    String middle();
    
    String right();

  }

  public interface Resources extends ClientBundle {
    Resources INSTANCE = (Resources) GWT.create(Resources.class);
    
    @Source("resources/ButtonBar.css")
    Css buttonBarCss();
  }

  private final Resources resources;
  
  private int currentOffset = 0;

  public ButtonBar() {
    this(Resources.INSTANCE);
  }
  
  public ButtonBar(Resources resources) {
    this.resources = resources;
    resources.buttonBarCss().ensureInjected();
    setStyleName(resources.buttonBarCss().buttonBar());
  }

  public ButtonBar add(Widget button, int width) {
    button.addStyleName(resources.buttonBarCss().button());
    button.getElement().getStyle().setLeft(currentOffset, Unit.PX);
    button.getElement().getStyle().setWidth(width, Unit.PX);

    if (getWidgetCount() == 0) {
      button.addStyleName(resources.buttonBarCss().left());
    } else {
      Widget oldButton = getWidget(getWidgetCount() - 1);
      if (getWidgetCount() > 1) {
        oldButton.removeStyleName(resources.buttonBarCss().right());
        oldButton.addStyleName(resources.buttonBarCss().middle());
      }
      button.addStyleName(resources.buttonBarCss().right());
    }
    add(button);
    currentOffset += width;

    return this;
  }

  public static ButtonBar create() {
    return new ButtonBar();
  }

  public static ButtonBar create(Resources resources) {
    return new ButtonBar(resources);
  }
  
}
