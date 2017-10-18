package webservices;

import beans.Bid;
import beans.Product;
import java.util.List;
import javax.jms.Message;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 *
 * @author Vidar
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface AuctionWebService {
    @WebMethod List<Product> getActiveProducts();
    @WebMethod Message bidForProduct(Bid newBid);
}
