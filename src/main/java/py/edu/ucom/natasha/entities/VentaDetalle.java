
package py.edu.ucom.natasha.entities;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author natas
 */
@Entity
@Table(name = "venta_detalle")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "VentaDetalle.findAll", query = "SELECT v FROM VentaDetalle v"),
        @NamedQuery(name = "VentaDetalle.findByVentaDetalleId", query = "SELECT v FROM VentaDetalle v WHERE v.ventaDetalleId = :ventaDetalleId"),
        @NamedQuery(name = "VentaDetalle.findByCantidad", query = "SELECT v FROM VentaDetalle v WHERE v.cantidad = :cantidad"),
        @NamedQuery(name = "VentaDetalle.findBySubtotal", query = "SELECT v FROM VentaDetalle v WHERE v.subtotal = :subtotal") })
public class VentaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "venta_detalle_id")
    private Integer ventaDetalleId;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "subtotal")
    private int subtotal;
    @JoinColumn(name = "producto_id", referencedColumnName = "producto_id")
    @ManyToOne(optional = false)
    private Producto productoId;
    @JoinColumn(name = "venta_id", referencedColumnName = "venta_id")
    @ManyToOne(optional = false)
    private Venta ventaId;

    public VentaDetalle() {
    }

    public VentaDetalle(Integer ventaDetalleId) {
        this.ventaDetalleId = ventaDetalleId;
    }

    public VentaDetalle(Integer ventaDetalleId, int cantidad, int subtotal) {
        this.ventaDetalleId = ventaDetalleId;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public Integer getVentaDetalleId() {
        return ventaDetalleId;
    }

    public void setVentaDetalleId(Integer ventaDetalleId) {
        this.ventaDetalleId = ventaDetalleId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProductoId() {
        return productoId;
    }

    public void setProductoId(Producto productoId) {
        this.productoId = productoId;
    }

    public Venta getVentaId() {
        return ventaId;
    }

    public void setVentaId(Venta ventaId) {
        this.ventaId = ventaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ventaDetalleId != null ? ventaDetalleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentaDetalle)) {
            return false;
        }
        VentaDetalle other = (VentaDetalle) object;
        if ((this.ventaDetalleId == null && other.ventaDetalleId != null)
                || (this.ventaDetalleId != null && !this.ventaDetalleId.equals(other.ventaDetalleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "creadordeentidades.VentaDetalle[ ventaDetalleId=" + ventaDetalleId + " ]";
    }

}
