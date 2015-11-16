public class Ring {

    double innerRadius; //内半径
    double outerRadius; //外半径
    String fillColor; //填充颜色

    //构造方法，用于创建环的对象
    Ring(double iRadius, double oRadius, String color) {
        innerRadius = iRadius; //初始化内半径
        outerRadius = oRadius; //初始化外半径
        fillColor = color; //初始化填充颜色
    }
    //思考题：构造方法的方法名前面不能有返回类型

    //为解决第2个思考题的错误增加无参构造方法，两个构造方法是overload关系
    Ring() {
        innerRadius = 1;
        outerRadius = 2;
        fillColor = "WHITE";
    }

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
}
