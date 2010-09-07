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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Image;

/**
 * @author jrydberg
 * 
 */
public class PopupButton extends Button {

  public interface Css extends CssResource {
    String button();

    String text();

    String right();

  }

  public interface Resources extends ClientBundle {
    @Source("resources/PopupButton.css")
    Css popupButtonCss();

    @Source("resources/PopupButton-arrows.png")
    ImageResource popupButtonArrows();
  }

  private static Resources DEFAULT_RESOURCES;

  private Css style;

  private Element textElement;

  private static Resources getDefaultResources() {
    if (DEFAULT_RESOURCES == null) {
      DEFAULT_RESOURCES = GWT.<Resources> create(Resources.class);
    }
    return DEFAULT_RESOURCES;
  }

  public PopupButton() {
    this(getDefaultResources());
  }
  
  public PopupButton(String text) {
    this(getDefaultResources(), text);
  }
  
  public PopupButton(Resources resources, String text) {
    this(resources);
    setText(text);
  }
  
  public PopupButton(Resources resources) {
    this.style = resources.popupButtonCss();
    style.ensureInjected();
    setStyleName(style.button());
    // Create layout of the widget:
    textElement = DOM.createSpan();
    textElement.setClassName(style.text());
    getElement().appendChild(textElement);
    // Add the image to the right:
    Element rightElement = DOM.createDiv();
    rightElement.setClassName(style.right());
    rightElement.appendChild(new Image(resources.popupButtonArrows())
        .getElement());
    getElement().appendChild(rightElement);
  }

  @Override
  public void setText(String text) {
    textElement.setInnerText(text);
  }
 
  @Override
  public String getText() {
    return textElement.getInnerText();
  }
  
}
