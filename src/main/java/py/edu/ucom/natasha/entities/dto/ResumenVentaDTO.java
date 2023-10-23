package py.edu.ucom.natasha.entities.dto;

import java.util.Date;
import java.util.List;

import py.edu.ucom.natasha.entities.VentaDetalle;

public class ResumenVentaDTO {

    public String razonSocial;
    public String documento;
    public Date fecha;
    public List<VentaDetalleDTO> detalle;

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<VentaDetalleDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<VentaDetalleDTO> detalle) {
        this.detalle = detalle;
    }

}
