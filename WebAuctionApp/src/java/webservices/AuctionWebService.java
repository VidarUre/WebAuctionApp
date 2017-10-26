package webservices;

import beans.Product;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 *
 * @author Vidar
 */
@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface AuctionWebService {
    @WebMethod(operationName = "getActiveProducts")
    List<Product> getActiveProducts();
    @WebMethod(operationName = "bidForProduct")
    Message bidForProduct(@WebParam(name = "bidderName") String bidderName, @WebParam(name = "productId") long productId, double amount);
}