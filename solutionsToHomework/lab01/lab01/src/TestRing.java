public class TestRing {

    public static void main(String[] args) {
        Ring ring = new Ring(5, 8, "RED");
        //思考题：如果把上面换成 new Ring(); ,则会提示无参构造方法不存在。
        //现在去加上无参构造方法
        System.out.println("内半径：" + ring.innerRadius);
        System.out.println("外半径：" + ring.outerRadius);
        System.out.println("填充颜色：" + ring.fillColor);
        System.out.println("环的面积：" + ring.getArea());
        System.out.println("环的内圆周长：" + ring.getInnerPerimeter());
        System.out.println("环的外圆周长：" + ring.getOuterPerimeter());
    }
}
