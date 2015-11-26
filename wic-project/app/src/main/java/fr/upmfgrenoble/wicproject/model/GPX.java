package fr.upmfgrenoble.wicproject.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import fr.upmfgrenoble.wicproject.Utils;

/**
 * Created by thibaud on 04/11/15.
 */
public class GPX extends LinkedList<GPX.Track> {

    public static class Track extends LinkedList<TrackSegment> {
    }

    public static class TrackSegment extends LinkedList<TrackPoint> {
    }

    public static class TrackPoint {
        public LatLng position;
    }

    public static GPX parse(InputStream inputStream) {

        GPX outputGPX = new GPX();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(inputStream);

            NodeList gpxNodes = doc.getElementsByTagName("gpx");
            if (gpxNodes.getLength() == 0) {
                Log.e(Utils.LOG_TAG, "It's not a GPX document");
                return outputGPX;
            } else if (gpxNodes.getLength() > 1) {
                Log.w(Utils.LOG_TAG, "W: There is more than one gpx tag");
            }

            NodeList trackNodes = ((Element) gpxNodes.item(0)).getElementsByTagName("trk");
            for (int i = 0; i < trackNodes.getLength(); ++i) {

                Track track = new Track();
                outputGPX.add(track);

                NodeList trackSegNodes = ((Element) trackNodes.item(i)).getElementsByTagName("trkseg");
                for (int j = 0; j < trackSegNodes.getLength(); ++j) {

                    TrackSegment trackSeg = new TrackSegment();
                    track.add(trackSeg);

                    NodeList trackPointNodes = ((Element) trackNodes.item(j)).getElementsByTagName("trkpt");
                    for (int k = 0; k < trackPointNodes.getLength(); ++k) {

                        Element trackPointNode = (Element) trackPointNodes.item(k);

                        if (!trackPointNode.hasAttribute("lat") || !trackPointNode.hasAttribute("lon")) {
                            continue;
                        }

                        TrackPoint trackPoint = new TrackPoint();
                        try {
                            trackPoint.position = new LatLng(Double.parseDouble(trackPointNode.getAttribute("lat")),
                                    Double.parseDouble(trackPointNode.getAttribute("lon")));
                        } catch (NumberFormatException e) {
                            continue;
                        }
                        trackSeg.add(trackPoint);
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputGPX;
    }
}
