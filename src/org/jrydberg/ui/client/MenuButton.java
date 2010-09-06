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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;

/**
 * @author jrydberg
 * 
 */
public class MenuButton extends Button implements ClickHandler {

  public interface Css extends CssResource {
    String button();
    
  }

  public interface Resources extends MenuBar.Resources {
    @Source("resources/MenuButton.css")
    Css menuButtonCss();

    @Source("resources/MenuButton-arrow.png")
    ImageResource menuButtonIcon();
  }

  private PopupMenu menu;
  
  private static Resources DEFAULT_RESOURCES;

  private static Resources getDefaultResources() {
    if (DEFAULT_RESOURCES == null) {
      DEFAULT_RESOURCES = GWT.<Resources> create(Resources.class);
    }
    return DEFAULT_RESOURCES;
  }
  
  public MenuButton(String text, PopupMenu menu) {
    this(getDefaultResources(), text, menu);
  }

  public MenuButton(ImageResource image, PopupMenu menu) {
    this(getDefaultResources(), new Image(image), menu);
  }

  public MenuButton(Resources resources, Image image, PopupMenu menu) {
    this(resources, menu);
    setImage(image);
  }

  public MenuButton(Resources resources, String text, PopupMenu menu) {
    this(resources, menu);
    setText(text);
  }

  public MenuButton(Resources resources, PopupMenu menu) {
    setStyleName(resources.menuButtonCss().button());
    resources.menuButtonCss().ensureInjected();
    // Setup the button content:
    getElement().appendChild(DOM.createSpan());
    Image image = new Image(resources.menuButtonIcon());
    getElement().appendChild(image.getElement());
    // Initialize popup-menu
    this.menu = menu;
    // Attach listener
    super.addClickHandler(this);
  }

  public void setImage(Image image) {
    getElement().removeChild(getElement().getChild(0));
    // Insert the image element:
    getElement().insertFirst(image.getElement());
  }

  public void setText(String text) {
    getElement().removeChild(getElement().getChild(0));
    Element textElement = DOM.createSpan();
    textElement.setInnerText(text);
    getElement().insertFirst(textElement);
  }

  @Override
  public void onClick(ClickEvent event) {
    menu.setPopupPositionAndShow(new PositionCallback() {
      @Override
      public void setPosition(int offsetWidth, int offsetHeight) {
        int top = (MenuButton.this.getAbsoluteTop() + MenuButton.this.getOffsetHeight());
        if ((top + offsetHeight + 50) >= Window.getClientHeight()) {
          top = MenuButton.this.getAbsoluteTop() - offsetHeight;
        } 

        int left = MenuButton.this.getAbsoluteLeft();
        menu.setPopupPosition(left, top);
      }
    });
  }
  
}
