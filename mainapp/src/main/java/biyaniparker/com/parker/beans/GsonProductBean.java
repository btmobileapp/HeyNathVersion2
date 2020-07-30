package biyaniparker.com.parker.beans;

import biyaniparker.com.parker.utilities.CommonUtilities;

/**
 * Created by bt on 08/13/2016.
 */
public class GsonProductBean
{
    public int ProductId,CategoryId,ClientId, SequenceNo;
    public String ProductCode, ProductName, StripCode,Details,IconThumb, IconFull, IconFull1,DeleteStatus,IsActive;
    public int PriceId;
    public long CreatedBy;
    public long ChangedBy;
    public String CreatedDate,ChangedDate;

    public float price;
    public String IconFull2;
    public String IconFull3;
    public String IconFull4;
    public String IconFull5;
    public String UnitName;
    public  String Remark;

    public ProductBean toProductBean()
    {
        ProductBean bean=new ProductBean();
        bean.productId=ProductId;
        bean.categoryId=CategoryId;
        bean.clientId=ClientId;
        bean.sequenceNo=SequenceNo;
        bean.productCode=ProductCode;
        bean.productName=ProductName;
        bean.stripCode=StripCode;
        bean.details=Details;
        bean.iconThumb=IconThumb;
        bean.iconFull=IconFull;
        bean.iconFull1=IconFull1;
        bean.deleteStatus=DeleteStatus;
        bean.isActive=IsActive;
        bean.priceId=PriceId;
        bean.createdBy=CreatedBy;
        bean.changedBy=ChangedBy;
        try
        {
            bean.createdDate= CommonUtilities.parseDate(CreatedDate);
        }
        catch (Exception e){}
        try
        {
            bean.changedDate= CommonUtilities.parseDate(ChangedDate);
        }
        catch (Exception e){}

        try
        {
            bean. price=price;
            bean. IconFull2=IconFull2;
            bean. IconFull3=IconFull3;
            bean. IconFull4=IconFull4;
            bean. IconFull5=IconFull5;
            bean. UnitName=UnitName;
            bean. Remark=Remark;
        }
        catch (Exception ed)
        {}
        return bean;
    }
}


