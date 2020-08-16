package biyaniparker.com.parker.beans;

/**
 * Created by bt18 on 08/30/2016.
 */
public class ProductBeanWithQnty extends ProductBean
{
    public boolean checkValue;
    public int qnt;

    public boolean getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(boolean checkValue) {
        this.checkValue = checkValue;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }
}
