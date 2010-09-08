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
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

/**
 * @author jrydberg
 *
 */
public class ToggleButton extends Button implements HasValue<Boolean >{

  public interface Resources extends Button.Resources {
    
  }

  private final HandlerManager handlerManager = new HandlerManager(this);
  
  private static Resources DEFAULT_RESOURCES;

  private Resources resources;
  
  private boolean value;
  
  private static Resources getDefaultResources() {
    if (DEFAULT_RESOURCES == null) {
      DEFAULT_RESOURCES = GWT.<Resources> create(Resources.class);
    }
    return DEFAULT_RESOURCES;
  }

  public ToggleButton() {
    this(getDefaultResources());
  }

  public ToggleButton(Resources resources) {
    super(resources);
    this.resources = resources;

    addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        setValue(!value, true);
      }
    });
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.HasValue#getValue()
   */
  @Override
  public Boolean getValue() {
    return value;
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
   */
  @Override
  public void setValue(Boolean value) {
    setValue(value, false);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object, boolean)
   */
  @Override
  public void setValue(Boolean value, boolean fireEvents) {
    if (value.booleanValue()) {
      addStyleName(resources.buttonCss().active());
    } else {
      removeStyleName(resources.buttonCss().active());
    }
    this.value = value;
    if (fireEvents) {
      ValueChangeEvent.fire(this, value);
    }
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
   */
  @Override
  public HandlerRegistration addValueChangeHandler(
      ValueChangeHandler<Boolean> handler) {
    return handlerManager.addHandler(ValueChangeEvent.getType(), handler);
  }
  
}
