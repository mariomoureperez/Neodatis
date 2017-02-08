package exercicio31;


import java.util.Date;
import java.util.Iterator;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.core.query.nq.SimpleNativeQuery;


public class Exercicio31 {

    public static void main(String[] args) {
        
        String ODB_NAME="BDNeodatis";
        ODB odb=ODBFactory.open(ODB_NAME);
       
       /* Sport deporte1= new Sport("volley_ball");
        Sport deporte2= new Sport("tennis");
        
        xogadores xogador1=new xogadores("olivier",new Date(),deporte1,1000);
        xogadores xogador2=new xogadores("pierre",new Date(),deporte1,1500);
        xogadores xogador3=new xogadores("elohim",new Date(),deporte1,2000);
        xogadores xogador4=new xogadores("minh",new Date(),deporte1,1300);
        xogadores xogador5=new xogadores("luis",new Date(),deporte2,1600);
        xogadores xogador6=new xogadores("carlos",new Date(),deporte2,2000);
        xogadores xogador7=new xogadores("luis",new Date(),deporte2,1500);
        xogadores xogador8=new xogadores("jose",new Date(),deporte2,3000);
        
        Equipos equipo1=new Equipos("Paris");
        Equipos equipo2=new Equipos("Montpellier");
        Equipos equipo3=new Equipos("Bordeux");
        Equipos equipo4=new Equipos("Lion");
        
        
        equipo1.addplayer(xogador1);
        equipo1.addplayer(xogador2);
        equipo2.addplayer(xogador3);
        equipo2.addplayer(xogador4);
        equipo3.addplayer(xogador5);
        equipo3.addplayer(xogador6);
        equipo4.addplayer(xogador7);
        equipo4.addplayer(xogador8);
        
        
        Partido partido1= new Partido(new Date(),deporte1,equipo1,equipo2);
        Partido partido2=new Partido(new Date(), deporte2, equipo3,equipo4);
        
        odb.store(partido1);
        odb.store(partido2);
        odb.commit();*/
        
        consultarNome(odb);
        conNoDeSa(odb);
        actuPorNomeXog(odb);
        xogVolley(odb);
        cambiarNome(odb);
        nomePrimLetraDepor(odb);
        borrarXog(odb);
        nomePractDeport(odb);      
        nomeSalMenos1500(odb);     
        actuSalario(odb);
        
           // Closes the database
        odb.close();     
      }
    public static void consultarNome(ODB odb){
         org.neodatis.odb.Objects<Sport> deportes = odb.getObjects(Sport.class);
        Sport sport=null;
        while(deportes.hasNext()){
            sport = deportes.next();
        System.out.println(sport.getName());
            }
    }
    public static void conNoDeSa(ODB odb){
        org.neodatis.odb.Objects<xogadores> jugadores = odb.getObjects(xogadores.class);
        xogadores xogador=null;
        while(jugadores.hasNext()){
            xogador = jugadores.next();
        System.out.println("Nombre: "+xogador.getName()+", camDepor favorito: "+xogador.getFavoriteSport().getName()+", salario: "+xogador.getSalario());
            }
    }
    public static void actuPorNomeXog(ODB odb){
         IQuery Qupdate = odb.criteriaQuery(xogadores.class,Where.equal("name", "luis"));
        Objects<xogadores> xogadorU = odb.getObjects(Qupdate);
        
        while(xogadorU.hasNext()){
            
         xogadores nome = (xogadores) xogadorU.next();
       
        nome.setName("Javi");
        //añadimos el nuevo nombre a la BD
        odb.store(nome);
    }
         }
    public static void xogVolley(ODB odb){
        IQuery conDep = odb.criteriaQuery(Sport.class, Where.equal("name", "volley_ball"));
            Sport camDepor= (Sport) odb.getObjects(conDep).getFirst();
            System.out.println(camDepor.getName());
           
            IQuery query2 = odb.criteriaQuery(xogadores.class, Where.equal("favoriteSport",camDepor));
           
            Objects<xogadores> players = odb.getObjects(query2);
           
            System.out.println(players.size());
           int i=0;
            while(players.hasNext()) {
                   i++;
            System.out.println(i+" "+players.next().getName());

            }
    }
    public static void cambiarNome(ODB odb){
        IQuery conDep = odb.criteriaQuery(Sport.class, Where.equal("name", "tennis"));
            Sport camDepor= (Sport) odb.getObjects(conDep).getFirst();
                        
            IQuery upTodos = odb.criteriaQuery(xogadores.class,Where.equal("favoriteSport", camDepor));
            Objects<xogadores> xogadorU = odb.getObjects(upTodos);
              
            while(xogadorU.hasNext()){
            
            xogadores nome = (xogadores) xogadorU.next();
       
            nome.setName("Juan");
            //añadimos el nuevo nombre a la BD
            odb.store(nome);
    }}
    public static void nomePrimLetraDepor(ODB odb){
    IQuery query = new SimpleNativeQuery() {
            public boolean match(xogadores player) {
            return player.getFavoriteSport().getName().toLowerCase().startsWith("volley");
            }
        };
        
        Objects<xogadores> players = odb.getObjects(query);
        System.out.println("Jugadores que practican volley_ball");
        
        int l=0;
        while (players.hasNext()) {
            l++;
        System.out.println(l + " " + players.next().getName());

        }
}
    public static void borrarXog(ODB odb){
         IQuery qBorrar = odb.criteriaQuery(xogadores.class, Where.equal("name", "Juan"));
    Objects<xogadores> players = odb.getObjects(qBorrar);

    while(players.hasNext()){
   
    odb.delete(players.next());
    }}
    public static void nomePractDeport(ODB odb){
         IQuery deport=odb.criteriaQuery(Sport.class, Where.equal("name","tennis"));
        Sport deporte=(Sport) odb.getObjects(deport).getFirst();
        System.out.println(deporte.getName());
        
        //si ponemos favoriteSport.name y el nombre del deporte no hace falta hacer la query de arriba.
                       
       IQuery query2 =odb.criteriaQuery(xogadores.class, Where.and().add(Where.equal("favoriteSport.name","tennis"))
                           .add(Where.equal("name", "luis")));
       Objects <xogadores> jugador=odb.getObjects(query2);
        
        while(jugador.hasNext()){
            System.out.println(jugador.next().getName()+" ");
        }
    }
    public static void nomeSalMenos1500(ODB odb){
             IQuery query3 = odb.criteriaQuery(xogadores.class, Where.lt("salario",1500));
             Objects <xogadores> jug=odb.getObjects(query3);
           
             while(jug.hasNext()){
                 xogadores x=jug.next();
                 
                 IQuery query4 = odb.criteriaQuery(Equipos.class, Where.contain("players",x));
                Objects<Equipos> equi= odb.getObjects(query4);
                
                while(equi.hasNext()){
                   Equipos e= equi.next();
                    System.out.println("Equipo: "+e.getName()+" Jugador: "+x.getName());
                }
                
             }
          }
    public static void actuSalario(ODB odb){
        IQuery query3=odb.criteriaQuery(Equipos.class, Where.and().add(Where.equal("name","Lion")));
        
        Equipos xoga=(Equipos) odb.getObjects(query3).getFirst();
        
        Iterator <xogadores> jug=xoga.players.iterator();
        
        
        while(jug.hasNext()){
            xogadores j=jug.next();
            System.out.println(j.getName()+" "+j.getSalario());
            if(j.getName().equals("luis")){
                j.setSalario(j.getSalario()+100);
                 odb.store(j);
        
            }
        }
    }
    
    }

    


