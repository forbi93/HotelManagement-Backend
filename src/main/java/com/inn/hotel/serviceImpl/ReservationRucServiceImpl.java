package com.inn.hotel.serviceImpl;

import com.inn.hotel.JWT.JwtFilter;
import com.inn.hotel.POJO.Reservation;
import com.inn.hotel.POJO.ReservationRuc;
import com.inn.hotel.constents.HotelConstants;
import com.inn.hotel.dao.ReservationRucDao;
import com.inn.hotel.service.ReservationRucService;
import com.inn.hotel.utils.HotelUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class ReservationRucServiceImpl implements ReservationRucService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    ReservationRucDao reservationRucDao;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("Inside generateReport");
        try {
            String fileName;
            if (validateRequestMap(requestMap)) {
                if (requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")) {
                    fileName = (String) requestMap.get("uuid");
                } else {
                    fileName = HotelUtils.getUUID();
                    requestMap.put("uuid", fileName);
                    insertReservation(requestMap);
                }

                String data = "\n" + "Razón Social: " + requestMap.get("razSocial") + "\n" + "RUC: " + requestMap.get("ruc") +
                        "\n" + "Dirección: " + requestMap.get("address") + "\n" + "Fecha de Emisión: " + requestMap.get("dateCreated") + "\n" +
                        "Método de pago: " + requestMap.get("paymentMethod");

                Document document = new Document();
                document.setPageSize(new Rectangle(380, PageSize.A4.getHeight()));
                PdfWriter.getInstance(document, new FileOutputStream(HotelConstants.STORE_LOCATION + "\\" + fileName + ".pdf"));

                document.open();

                Paragraph chunk = new Paragraph("FACTURA" + "\n" +  "La Casa del Turista" + "\n" +  "RUC: 12368548923" + "\n" + "C. Cesareo Chacaltana 130 B, Lima 15074, Miraflores" + "\n" +  requestMap.get("uuid") + "\n", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph = new Paragraph(data + "\n \n", getFont("Data"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray = HotelUtils.getJsonArrayFromString((String) requestMap.get("roomDetails"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    addRows(table, HotelUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("Total :" + requestMap.get("totalAmount") + "\n"
                        + "Gracias por visitarnos. Visítenos nuevamente!!", getFont("Data"));
                document.add(footer);
                document.close();
                return new ResponseEntity<>("{\"uuid\":\"" + fileName + "\"}", HttpStatus.OK);
            }
            return HotelUtils.getResponseEntity("Required data not found.", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRows");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("typeRoom"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));
    }

    private void addTableHeader(PdfPTable table) {
        log.info("Inside addTableHeader");
        Stream.of("Habitación", "Tipo", "Horas", "Precio por Hora", "Monto total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setBorderWidthLeft(0);
                    header.setBorderWidthRight(0);
                    header.setBorderColorTop(BaseColor.BLACK);
                    header.setBorderColorBottom(BaseColor.BLACK);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.WHITE);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);

                });
    }

    private Font getFont(String type) {
        log.info("Inside getFont");
        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 14, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("Inside setRectangleInPdf");
        Rectangle rect = new Rectangle(577, 825, 300, 15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);
    }

    private void insertReservation(Map<String, Object> requestMap) {
        try {
            ReservationRuc reservation = new ReservationRuc();
            reservation.setUuid((String) requestMap.get("uuid"));
            reservation.setRazSocial((String) requestMap.get("razSocial"));
            reservation.setRuc((String) requestMap.get("ruc"));
            reservation.setDateCreated((String) requestMap.get("dateCreated"));
            reservation.setAddress((String) requestMap.get("address"));
            reservation.setPaymentMethod((String) requestMap.get("paymentMethod"));
            reservation.setDate((String) requestMap.get("date"));
            reservation.setTotal(Integer.parseInt((String) requestMap.get("totalAmount")));
            reservation.setRoomDetail((String) requestMap.get("roomDetails"));
            reservation.setCreatedBy(jwtFilter.getcurrentUser());
            reservationRucDao.save(reservation);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("razSocial") &&
                requestMap.containsKey("ruc") &&
                requestMap.containsKey("dateCreated") &&
                requestMap.containsKey("address") &&
                requestMap.containsKey("date") &&
                requestMap.containsKey("paymentMethod") &&
                requestMap.containsKey("roomDetails") &&
                requestMap.containsKey("totalAmount");
    }

    @Override
    public ResponseEntity<java.util.List<ReservationRuc>> getReservations() {
        List<ReservationRuc> list = new ArrayList<>();
        if (jwtFilter.isAdmin()) {
            list = reservationRucDao.getAllReservationsRuc();
        } else {
            list = reservationRucDao.getReservationRucByUserName(jwtFilter.getcurrentUser());
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("Inside getPdf : requestMap {}", requestMap);
        try {
            byte[] byteArray = new byte[0];
            if (!requestMap.containsKey("uuid") && validateRequestMap(requestMap))
                return new ResponseEntity<>(byteArray, HttpStatus.BAD_REQUEST);
            String filePath = HotelConstants.STORE_LOCATION + "\\" + (String) requestMap.get("uuid") + ".pdf";
            if (HotelUtils.isFileExist(filePath)) {
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            } else {
                requestMap.put("isGenerate", false);
                generateReport(requestMap);
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray,HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteReservation(Integer id) {
        try{
            Optional optional = reservationRucDao.findById(id);
            if (!optional.isEmpty()){
                reservationRucDao.deleteById(id);
                return HotelUtils.getResponseEntity("Reservation Deleted Successfully",HttpStatus.OK);
            }
            return HotelUtils.getResponseEntity("Reservation id does not exist", HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private byte[] getByteArray(String filePath) throws Exception {
        File initialFile = new File(filePath);
        InputStream targetStream = new FileInputStream(initialFile);
        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;
    }

}
