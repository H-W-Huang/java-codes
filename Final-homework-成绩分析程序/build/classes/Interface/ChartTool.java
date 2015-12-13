/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;

/**
 *
 * @author H.W
 */
public interface ChartTool {

    public abstract BarChart<String, Number> getBarChart();
    
    public abstract PieChart getPieChart();
    
}
