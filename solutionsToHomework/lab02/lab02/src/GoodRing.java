public class GoodRing {

    //数据域封装，先把数据域用private修饰，setter和getter方法见类定义的最后面
    private double innerRadius; //内半径
    private double outerRadius; //外半径
    private String fillColor; //填充颜色

    //构造方法，构造方法通常使用public修饰
    public GoodRing(double iRadius, double oRadius, String color) {
        innerRadius = iRadius; //初始化内半径
        outerRadius = oRadius; //初始化外半径
        fillColor = color; //初始化填充颜色
    }
    public GoodRing() {
        innerRadius = 1;
        outerRadius = 2;
        fillColor = "WHITE";
    }

    //3个功能方法，需要在其它类（如主类）中使用，因此使用public修饰
    //计算并返回圆面积的方法
    double getArea() {
        return (outerRadius * outerRadius - innerRadius * innerRadius) * Math.PI;
    }

    //步骤5增加2表计算周长方法
    double getInnerPerimeter() {
        return 2 * Math.PI * innerRadius;
    }

    double getOuterPerimeter() {
        return 2 * Math.PI * outerRadius;
    }

    //下面是封装数据域编写的setter和getter, 都用public 修饰-----------------------
    public double getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(double innerRadius) {
        this.innerRadius = innerRadius;
    }

    public double getOuterRadius() {
        return outerRadius;
    }

    public void setOuterRadius(double outerRadius) {
        this.outerRadius = outerRadius;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }
}
