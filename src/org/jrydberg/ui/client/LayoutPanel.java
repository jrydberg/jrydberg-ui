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
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

/**
 * @author jrydberg
 *
 */
public class LayoutPanel extends Composite {

  
  /**
   * Css resource for the {@link LayoutPanel}.
   */
  public interface Css extends CssResource {
    String layout();
  
  }
  
  public interface Resources extends ClientBundle {
    public static final Resources INSTANCE = GWT.create(Resources.class);
    
    @Source("resources/LayoutPanel.css")
    Css layoutPanelCss();

  }
  
  /**
   * Layout data for a single widget that has been added to
   * the {@link LayoutPanel}.
   */  
  class Layer {
    
    private Widget widget;
    
    private int topPos = 0;
    private int leftPos = 0;

    private int width;
    private int height;
    
    private HasVerticalAnchors vAnchor = HasVerticalAnchors.TOP;
    private HasHorizontalAnchors hAnchor = HasHorizontalAnchors.LEFT;
    
    Layer(Widget widget) {
      this.widget = widget;
    }

    /**
     * .Position this layer in the layout panel
     * 
     * @param leftPos left position in the layout panels coordinate space
     * @param topPos top position in the layout panels coordinate space
     * @return reference to this {@link Layer}
     */
    public Layer at(int leftPos, int topPos) {
      this.leftPos = leftPos;
      this.topPos = topPos;
      return this;
    }

    /**
     * Set the size of this layer in the layout panel.
     * 
     * @param width width in the layout panels coordinate space
     * @param height height in the layouts panels coordinate space
     * @return reference to this {@link Layer}
     */
    public Layer size(int width, int height) {
      this.width = width;
      this.height = height;
      return this;
    }

    /**
     * Anchor this layer vertically in the layout panel.
     * 
     * @param vAnchor the vertical anchor 
     * @return reference to this {@link Layer}
     */
    public Layer anchor(HasVerticalAnchors vAnchor) {
      this.vAnchor = vAnchor;
      return this;
    }
    
    /**
     * Anchor this layer horizontally in the layout panel.
     * 
     * @param hAnchor the horizontal anchor
     * @return reference to this {@link Layer}
     */
    public Layer anchor(HasHorizontalAnchors hAnchor) {
      this.hAnchor = hAnchor;
      return this;
    }

    /**
     * Set vertical alignment of the widget that this layer positions.
     * What this mean is up to the widget to interpret.
     * 
     * This only works on widgets that implements the 
     * {@link HasVerticalAlignment} interface.
     * 
     * @param alignment the vertical alignment
     * @return reference to this {@link Layer}
     */
    public Layer align(VerticalAlignmentConstant alignment) {
      if (widget instanceof HasVerticalAlignment) {
        ((HasVerticalAlignment)widget).setVerticalAlignment(alignment);
      }
      return this;
    }
    
    /**
     * Set horizontal alignment of the widget that this layer positions.
     * What this mean is up to the widget to interpret.
     * 
     * This only works on widgets that implements the
     * {@link HasHorizontalAlignemnt} interface.
     * 
     * @param alignment the horizontal alignment
     * @return reference to this {@link Layer}
     */
    public Layer align(HorizontalAlignmentConstant alignment) {
      if (widget instanceof HasHorizontalAlignment) {
        ((HasHorizontalAlignment)widget).setHorizontalAlignment(alignment);
      }
      return this;
    }
    
    void layout() {
      Style style = widget.getElement().getStyle();
      
      switch (vAnchor) {
      case TOP:
        style.setTop(topPos, Unit.PX);
        style.setHeight(height, Unit.PX);
        break;
      case BOTTOM:
        style.setBottom(layoutHeight - topPos - height, Unit.PX);
        style.setHeight(height, Unit.PX);
        break;
      case CENTER:
        style.setHeight(height, Unit.PX);
        style.setProperty("marginTop", "auto");
        style.setProperty("marginBottom", "auto");
        style.setTop(0, Unit.PX);
        style.setBottom(0, Unit.PX);
        break;
      case BOTH:
        style.setTop(topPos, Unit.PX);
        style.setBottom(layoutHeight - topPos - height, Unit.PX);
        break;
      case NONE:
        style.setHeight(height, Unit.PX);
        break;
      }
      
      switch (hAnchor) {
      case LEFT:
        style.setLeft(leftPos, Unit.PX);
        style.setWidth(width, Unit.PX);
        break;
      case RIGHT:
        style.setRight(layoutWidth - leftPos - width, Unit.PX);
        style.setWidth(width, Unit.PX);
        break;
      case CENTER:
        style.setWidth(width, Unit.PX);
        style.setProperty("marginLeft", "auto");
        style.setProperty("marginRight", "auto");
        style.setLeft(0, Unit.PX);
        style.setRight(0, Unit.PX);
        break;
      case BOTH:
        style.setLeft(leftPos, Unit.PX);
        style.setRight(layoutWidth - leftPos - width, Unit.PX);
        break;
      case NONE:
        style.setWidth(width, Unit.PX);
        break;
      }      
      
    }
    
  }

  private final FlowPanel basePanel;
  
  protected final Resources resources;
  
  int layoutWidth;
  int layoutHeight;
  
  public LayoutPanel(int layoutWidth, int layoutHeight) {      
    this(layoutWidth, layoutHeight, Resources.INSTANCE);
  }

  public LayoutPanel(int layoutWidth, int layoutHeight, 
        Resources resources) {
    basePanel = new FlowPanel();
    initWidget(basePanel);
    this.layoutWidth = layoutWidth;
    this.layoutHeight = layoutHeight;
    this.resources = resources;
    resources.layoutPanelCss().ensureInjected();
  }
  
  public Layer add(Widget widget) {
    basePanel.add(widget);
    Layer layer = new Layer(widget);
    widget.addStyleName(resources.layoutPanelCss().layout());
    widget.setLayoutData(layer);
    return layer;
  }
  
  @Override
  public void onLoad() {
    for (Widget child : basePanel) {
      Layer layer = (Layer) child.getLayoutData();
      layer.layout();
    }
  }   
    
  public void onResize() {
    for (Widget child : basePanel) {
      if (child instanceof RequiresResize) {
        ((RequiresResize) child).onResize();
      }
    }
  }
  
}
