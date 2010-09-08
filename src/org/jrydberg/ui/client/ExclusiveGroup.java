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

import java.util.ArrayList;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

/**
 * @author jrydberg
 *
 */
public class ExclusiveGroup implements ValueChangeHandler<Boolean> {

  private ArrayList<HasValue<Boolean>> buttons = 
    new ArrayList<HasValue<Boolean>>();
  
  private ArrayList<HandlerRegistration> handlers = 
    new ArrayList<HandlerRegistration>();

  public static ExclusiveGroup create(HasValue<Boolean> ... buttons) {
    ExclusiveGroup group = new ExclusiveGroup();
    for (HasValue<Boolean> b : buttons)
      group.add(b);
    return group;
  }
  
  public ExclusiveGroup add(HasValue<Boolean> button) {
    // FIXME: ugly
    buttons.add(button);
    handlers.add(button.addValueChangeHandler(this));
    return this;
  } 
  
  public ExclusiveGroup add(HasValue<Boolean> ... newButtons) {
    for (HasValue<Boolean> b : newButtons) {
      this.add(b);
    }
    return this;
  }
  
  /* (non-Javadoc)
   * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
   */
  @Override
  public void onValueChange(ValueChangeEvent<Boolean> event) {
    for (HasValue<Boolean> button : buttons) {
      if (button != event.getSource()) {
        button.setValue(false, false);
      }
    }
  }

  /**
   * Remove all buttons from the group.
   */
  void clear() {
    while (handlers.size() != 0) {
      handlers.remove(0).removeHandler();
    }
    buttons.clear();
  }
  
}
