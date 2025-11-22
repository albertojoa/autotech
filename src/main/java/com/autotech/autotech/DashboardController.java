package com.autotech.autotech;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.autotech.autotech.clientes.ClienteRepository;
import com.autotech.autotech.compras.CompraRepository;
import com.autotech.autotech.productos.ProductoRepository;
import com.autotech.autotech.proveedores.ProveedorRepository;
import com.autotech.autotech.ventas.VentaRepository;

@Controller
public class DashboardController {

  private final ClienteRepository clienteRepository;
  private final ProveedorRepository proveedorRepository;
  private final ProductoRepository productoRepository;
  private final VentaRepository ventaRepository;
  private final CompraRepository compraRepository;

  public DashboardController(
      ClienteRepository clienteRepository,
      ProveedorRepository proveedorRepository,
      ProductoRepository productoRepository,
      VentaRepository ventaRepository,
      CompraRepository compraRepository) {
    this.clienteRepository = clienteRepository;
    this.proveedorRepository = proveedorRepository;
    this.productoRepository = productoRepository;
    this.ventaRepository = ventaRepository;
    this.compraRepository = compraRepository;
  }

  @GetMapping("/dashboard")
  public String dashboard(Model model) {
    LocalDate hoy = LocalDate.now();
    model.addAttribute("totalClientes", clienteRepository.count());
    model.addAttribute("totalProveedores", proveedorRepository.count());
    model.addAttribute("totalProductos", productoRepository.count());
    model.addAttribute("ventasHoy", ventaRepository.countByFecha(hoy));
    model.addAttribute("comprasHoy", compraRepository.countByFecha(hoy));
    model.addAttribute("stockBajo", productoRepository.countByStockLessThanEqual(5));
    return "dashboard"; // templates/dashboard.html
  }
}
