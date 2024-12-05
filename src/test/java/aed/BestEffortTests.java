package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class BestEffortTests {

    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;


    @BeforeEach
    void init(){
        //Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 7;
        listaTraslados = new Traslado[] {
                                            new Traslado(1, 0, 1, 100, 10),
                                            new Traslado(2, 0, 1, 400, 20),
                                            new Traslado(3, 3, 4, 500, 50),
                                            new Traslado(4, 4, 3, 500, 11),
                                            new Traslado(5, 1, 0, 1000, 40),
                                            new Traslado(6, 1, 0, 1000, 41),
                                            new Traslado(7, 6, 3, 2000, 42)
                                        };
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2) encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
        }
    }

    @Test
    void despachar_con_mas_ganancia_de_a_uno(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(1);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());
    }
    
    @Test
    void despachar_con_mas_ganancia_de_a_varios(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }
    
    @Test
    void despachar_mas_viejo_de_a_uno(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        
        sis.despacharMasAntiguos(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());
    }
    
    @Test
    void despachar_mas_viejo_de_a_varios(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        
        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        
    }
    
    @Test
    void despachar_mixtos(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);
        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        
    }
    
    @Test
    void agregar_traslados(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 0, 1, 10001, 5),
            new Traslado(9, 0, 1, 40000, 2),
            new Traslado(10, 0, 1, 50000, 3),
            new Traslado(11, 0, 1, 50000, 4),
            new Traslado(12, 1, 0, 150000, 1)
        };

        sis.registrarTraslados(nuevos);

        sis.despacharMasAntiguos(4);
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

    }
    
    @Test
    void promedio_por_traslado(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3);
        assertEquals(333, sis.gananciaPromedioPorTraslado());

        sis.despacharMasRedituables(3);
        assertEquals(833, sis.gananciaPromedioPorTraslado());

        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 1, 2, 1452, 5),
            new Traslado(9, 1, 2, 334, 2),
            new Traslado(10, 1, 2, 24, 3),
            new Traslado(11, 1, 2, 333, 4),
            new Traslado(12, 2, 1, 9000, 1)
        };

        sis.registrarTraslados(nuevos);
        sis.despacharMasRedituables(6);

        assertEquals(1386, sis.gananciaPromedioPorTraslado());
        

    }

    @Test
    void mayor_superavit(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 3, 4, 1, 7),
            new Traslado(7, 6, 5, 40, 6),
            new Traslado(6, 5, 6, 3, 5),
            new Traslado(2, 2, 1, 41, 4),
            new Traslado(3, 3, 4, 100, 3),
            new Traslado(4, 1, 2, 30, 2),
            new Traslado(5, 2, 1, 90, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(2);
        assertEquals(3, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(3);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

    }

    @Test
    void ciudades_traslados_vacios() {
        BestEffort sis = new BestEffort(this.cantCiudades, new Traslado[] {});
        assertSetEquals(new ArrayList<>(), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(), sis.ciudadesConMayorPerdida());
    }
    
    @Test
    void despachar_same_profit_and_age() {
    Traslado[] nuevos = new Traslado[] {
        new Traslado(1, 0, 1, 500, 10),
        new Traslado(2, 1, 0, 500, 10),
        new Traslado(3, 2, 3, 500, 10),
        new Traslado(4, 3, 2, 500, 10)
    };

    BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

    sis.despacharMasRedituables(2);
    assertSetEquals(new ArrayList<>(Arrays.asList(0, 1)), sis.ciudadesConMayorGanancia());
    assertSetEquals(new ArrayList<>(Arrays.asList(1, 0)), sis.ciudadesConMayorPerdida());

    sis.despacharMasAntiguos(2);
    assertSetEquals(new ArrayList<>(Arrays.asList(2, 3)), sis.ciudadesConMayorGanancia());
    assertSetEquals(new ArrayList<>(Arrays.asList(3, 2)), sis.ciudadesConMayorPerdida());
    }

    @Test
    void despachar_more_than_available() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasRedituables(20);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());
    }

    @Test
    void large_number_of_traslados() {
        ArrayList<Traslado> nuevos = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            nuevos.add(new Traslado(i, i % 7, (i + 1) % 7, i * 100, i));
        }

        BestEffort sis = new BestEffort(this.cantCiudades, nuevos.toArray(new Traslado[0]));

        sis.despacharMasRedituables(500);
        assertTrue(sis.gananciaPromedioPorTraslado() > 0);

        sis.despacharMasAntiguos(500);
        assertEquals(0, sis.gananciaPromedioPorTraslado());
    }

    @Test
    void mix_dispatch_frequent_city() {
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 0, 1, 500, 10),
            new Traslado(2, 0, 1, 400, 20),
            new Traslado(3, 0, 1, 600, 30),
            new Traslado(4, 0, 1, 1000, 5),
            new Traslado(5, 1, 2, 100, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);
    
        sis.despacharMasRedituables(3);
        assertEquals(0, sis.ciudadConMayorSuperavit());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
    }

    @Test
    void mixed_gains_and_losses() {
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 0, 1, 1000, 5),
            new Traslado(2, 1, 0, -500, 3),
            new Traslado(3, 2, 3, 700, 2),
            new Traslado(4, 3, 2, -900, 1),
            new Traslado(5, 4, 5, 0, 4)
        };
    
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);
    
        sis.despacharMasAntiguos(3);
        assertEquals(1, sis.ciudadConMayorSuperavit());
    
        sis.despacharMasRedituables(2);
        assertEquals(2, sis.ciudadConMayorSuperavit());
    }

    @Test
    void all_cities_equal_gain_loss() {
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 0, 1, 500, 5),
            new Traslado(2, 1, 2, -500, 5),
            new Traslado(3, 2, 3, 500, 5),
            new Traslado(4, 3, 4, -500, 5)
        };
    
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);
        sis.despacharMasRedituables(4);
    
        assertSetEquals(new ArrayList<>(), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(), sis.ciudadesConMayorPerdida());
    }

    @Test
    void edge_case_small_values() {
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 0, 1, 1, 1),
            new Traslado(2, 1, 2, 0, 2),
            new Traslado(3, 2, 3, -1, 3),
            new Traslado(4, 3, 4, 0, 4)
        };
    
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);
    
        sis.despacharMasRedituables(1);
        assertEquals(0, sis.ciudadConMayorSuperavit());
    
        sis.despacharMasAntiguos(1);
        assertEquals(1, sis.ciudadConMayorSuperavit());
    }
        
}
