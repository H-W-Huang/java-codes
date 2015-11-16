public class TestGoodRing {

    public static void main(String[] args) {
        GoodRing ring = new GoodRing(5, 8, "RED");

        System.out.println("内半径：" + ring.getInnerRadius());
        System.out.println("外半径：" + ring.getOuterRadius());
        System.out.println("填充颜色：" + ring.getFillColor());
        System.out.println("环的面积：" + ring.getArea());
        System.out.println("环的内圆周长：" + ring.getInnerPerimeter());
        System.out.println("环的外圆周长：" + ring.getOuterPerimeter());

        System.out.println("修改前环的面积：" + ring.getArea());
        modifyRing(ring, 5);
        System.out.println("修改后环的面积：" + ring.getArea());

        //思考题：步骤4中两个输出语句的结果的面积不同。
        //原因：参数传递的是对象的引用（即地址），因此在被调用方法中把环对象的外半径增加了
    }

    //该方法用于演示类作为方法返回值
    public static GoodRing createGoodRing(double iR, double oR, String c) {
        return new GoodRing(iR, oR, c);
    }

    //该方法用于演示类作为方法的参数
    public static void modifyRing(GoodRing aRing, double d) {
        aRing.setOuterRadius(aRing.getOuterRadius() + d);
    }

}
