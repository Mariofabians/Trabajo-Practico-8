package tp8;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Compra {

	public static void main(String[] args) {
		
		List <Producto> productos=new ArrayList<>();
		
		File f=new File("C:\\Users\\mario\\Desktop\\Productos.txt");
		String linea;
		Scanner sn=null;
		
		try
		{
			sn=new Scanner(f);
			
			while(sn.hasNextLine())
			{
					linea=sn.nextLine();
					cargarProductos(linea,productos);
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.print(e.getMessage());
		}
		finally
		{
			sn.close();
		}
		
		Persona pers1=new Persona ("Mario","Schujovitzky",36145563);
		Carrito carr=new Carrito (18,pers1);
		
		List<ItemCarrito> item = new ArrayList<>();
		int i=0;
		
		for (Producto producto:productos)
		{
			item.add(new ItemCarrito(carr,productos,i+1,i));
			i++;
		}
		
		LocalDate fecha=LocalDate.now();
		int dia=fecha.getDayOfMonth();
		char tipoDesc=' ';
		if (dia>0 && dia<=10)
		{
			tipoDesc='f'; // Del día 1 al 10 -> Descuento Fijo
		}
		else
		{
			if (dia>10 && dia<=20)
			{
				tipoDesc='p'; // Del día 11 al 20 -> Descuento por Porcentaje
			}
			else
			{
				if (dia>20 && dia<=31)
				{
					tipoDesc='t'; // Del día 21 en adelante -> Descuento Porcentaje por Tope
				}
			}
		}
		
		Scanner sn2=new Scanner(System.in);
		double montoDesc=0.0;
		System.out.print("Ingrese el valor del descuento: ");
		boolean rep=true;
		do {
		try {
		montoDesc = sn2.nextDouble();
		rep=false;
		}
		catch (InputMismatchException e) {	
        System.out.println("\nHa ocurrido un error");
		System.out.print("Ingrese el valor del descuento: ");
        sn2.nextLine();
		}
		} while(rep);
		
		//Verificar que no se aplique un descuento mayor al 100%
		if(tipoDesc=='p' || tipoDesc=='t')
		{
			while (montoDesc>=100)
			{
				System.out.println("\nHa ocurrido un error");
				System.out.print("Ingrese un valor de descuento menor al 100%: ");
				montoDesc=sn2.nextDouble();
			}
		}
		sn2.close();
				
		Descuento d=cargarDescuento(tipoDesc,montoDesc);
		mostrarCompra(item,pers1,carr,d);
	}

	private static void cargarProductos(String frase, List<Producto> list) 
	{
		String items[]=frase.split("	");
		
		int codigo=Integer.parseInt(items[0]);
		String nombre=items[1];
		String descripcion=items[2];
		double precio=Double.parseDouble(items[3]);
		int stock=Integer.parseInt(items[4]);
		list.add(new Producto(codigo,nombre,descripcion,precio,stock));
	}

	public static void mostrarCompra(List<ItemCarrito> item,Persona per,Carrito car,Descuento desc)
	{
		double suma=0.0;
		System.out.println("\nCarrito número "+car.getNumCarrito()+" del cliente "+per.dar_nombre());
		System.out.print("\n");
		item.get(0).mostrarTitulo();
		for(ItemCarrito a:item)
		{
			a.mostrarItem();
			suma+=a.getMontoItem();
		}
		System.out.println("\nSuma:$"+suma);
		
		//Verificar que no se aplique descuento a un carrito con valor 0
		if (suma>0)
		{
			//Verificar que el descuento no sea mayor al monto del carrito
			if (desc.valorFinal(suma)>0)
			{
				System.out.println("El descuento es de: "+desc.dameDescuento());
				System.out.println("La suma con descuento es: $"+desc.valorFinal(suma));
			}
			else
			{
				System.out.println("No se aplica descuento por ser mayor al valor de Carrito");
			}
		}
		else
		{
			System.out.println("No se pudo aplicar el descuento al carrito por tener valor inferior a 0");
		}
	}
	
	public static Descuento cargarDescuento(char desc,double monto)
	{
		Descuento des=null;
		switch(desc)
		{
			case 'f':
			{
				des=new DescuentoFijo();
				des.setValorDesc(monto);
				break;
			}
			
			case 'p':
			{
				des=new DescuentoPorc();
				des.setValorDesc(monto);
				break;
			}
			
			case 't':
			{
				des=new DescuentoPorcentajeConTope();
				des.setValorDesc(monto);
				break;
			}
		}
		return des;
	}
}
