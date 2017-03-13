package in.magizhchi.nigazhchi.refapp.cxf;

import in.magizhchi.nigazhchi.refapp.cxf.intf.VenueRESTService;
import in.magizhchi.nigazhchi.refapp.model.vo.VenueVO;
import in.magizhchi.nigazhchi.refapp.service.intf.VenueACS;

import java.io.IOException;

import javax.ws.rs.GET;

import org.apache.felix.scr.annotations.Reference;

import com.fasterxml.jackson.databind.ObjectMapper;


public class VenueRESTServiceImpl implements VenueRESTService {

  @Reference
  private VenueACS venueACS;


  @Override
  public void createVenue(String venueInfo) {

    VenueVO venueVO = null;

    ObjectMapper objMapper = new ObjectMapper();

    try {

      venueVO = objMapper.readValue(venueInfo.getBytes(), VenueVO.class);

      venueACS.createVenueDBVO(venueVO);

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }

  @GET
  @Override
  public VenueVO getVenue(String venueCode) {

    venueACS.getVenueDBVO(venueCode);

    return null;
  }

}
