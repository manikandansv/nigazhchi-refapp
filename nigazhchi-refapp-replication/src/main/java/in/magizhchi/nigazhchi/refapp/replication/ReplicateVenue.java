package in.magizhchi.nigazhchi.refapp.replication;

import in.magizhchi.nigazhchi.refapp.model.VenueDBVO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReplicateVenue {

  public void replicateVenueData(String path) throws IOException {

    InputStream is = new FileInputStream(new File(path));
    
    BufferedReader br = new BufferedReader(new InputStreamReader(is));

    List<VenueDBVO> venues =
        br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
//
//    JSONArray objects =
//        new JSONArray(Files.readAllLines(Paths.get(path)).stream()
//            .map(s -> new VenueDBVO(s.split(",")[0], s.split(",")[1])).collect(Collectors.toList()));


  }
  
  private Function<String, VenueDBVO> mapToItem = (line) -> {
    
    String[] p = line.split(",");// a CSV has comma separated lines
    
    VenueDBVO item = new VenueDBVO();
    
    return item;
    
  };

  public static void main(String args[]) throws IOException {

    new ReplicateVenue().replicateVenueData("src/main/resources/venue.csv");

  }

}
