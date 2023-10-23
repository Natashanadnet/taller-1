
package py.edu.ucom.natasha.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 *
 * @author natas
 */
@Entity
@Table(name = "venta")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v"),
        @NamedQuery(name = "Venta.findByVentaId", query = "SELECT v FROM Venta v WHERE v.ventaId = :ventaId"),
        @NamedQuery(name = "Venta.findByTotal", query = "SELECT v FROM Venta v WHERE v.total = :total"),
        @NamedQuery(name = "Venta.findByFecha", query = "SELECT v FROM Venta v WHERE v.fecha = :fecha") })
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "venta_id")
    private Integer ventaId;
    @Basic(optional = false)
    @Column(name = "total")
    private int total;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    @ManyToOne(optional = false)
    private Cliente clienteId;
    @JoinColumn(name = "metodo_pago_id", referencedColumnName = "metodo_pago_id")
    @ManyToOne(optional = false)
    private MetodoPago metodoPagoId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ventaId")
    private List<VentaDetalle> ventaDetalleList;

    public Venta() {
    }

    public Venta(Integer ventaId) {
        this.ventaId = ventaId;
    }

    public Venta(Integer ventaId, int total, Date fecha) {
        this.ventaId = ventaId;
        this.total = total;
        this.fecha = fecha;
    }

    public Integer getVentaId() {
        return ventaId;
    }

    public void setVentaId(Integer ventaId) {
        this.ventaId = ventaId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    public MetodoPago getMetodoPagoId() {
        return metodoPagoId;
    }

    public void setMetodoPagoId(MetodoPago metodoPagoId) {
        this.metodoPagoId = metodoPagoId;
    }

    @XmlTransient
    public List<VentaDetalle> getVentaDetalleList() {
        return ventaDetalleList;
    }

    public void setVentaDetalleList(List<VentaDetalle> ventaDetalleList) {
        this.ventaDetalleList = ventaDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ventaId != null ? ventaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.ventaId == null && other.ventaId != null)
                || (this.ventaId != null && !this.ventaId.equals(other.ventaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "creadordeentidades.Venta[ ventaId=" + ventaId + " ]";
    }

}
