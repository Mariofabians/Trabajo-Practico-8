package tp8;

import java.util.List;

public class ItemCarrito {
	private List<Producto> prod;
	private double montoItem;
	private double montoTotal;
	private int cantidad;
	private int i;
	
	public ItemCarrito (Carrito car, List<Producto> productos, int cant, int i)
	{
		car.getNumCarrito();
		prod=productos;
		cantidad=cant;
		montoItem=productos.get(i).getPrecio()*cantidad;
		montoTotal=0.0;
		this.i=i;
	}
	
	public double getMontoItem() {
		return montoItem;
	}
	
	public double getMontoTotal()
	{
		return montoTotal;
	}
	
	public void mostrarTitulo() {
		System.out.println("Código\t\tProducto\t\t\tCantidad\t  Precio  \t\tSubTotal");
	}
	
	public void mostrarItem()
	{
		System.out.println(prod.get(i).getCodigo()+"\t\t"+prod.get(i).getNombre()+" "+prod.get(i).getDesc()+"\t\t  "+cantidad+"\t\t  "+prod.get(i).getPrecio()+"\t\t  "+montoItem);
	}
}
