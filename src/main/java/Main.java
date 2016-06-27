/**
 * Created by Francis Cáceres on 12/6/2016.
 */

import freemarker.template.Configuration;
import modulo.Articulo;
import modulo.Comentario;
import modulo.Etiqueta;
import modulo.Usuario;
import servicios.ArticuloQueries;
import servicios.ComentarioQueries;
import servicios.EtiquetaQueries;
import servicios.UsuarioQueries;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.*;

import static spark.Spark.*;

public class Main {

    public static void main(String [] args)
    {
        //Al correr el programa por primera vez, cambiar el update en el persistence xml por create

        staticFileLocation("/recursos");

        //if(UsuarioQueries.getInstancia().find("er12")==null)
//        UsuarioQueries.getInstancia().crear(new Usuario("er12","Ernesto Rodríguez","1234",true, true));

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine( configuration );

        get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Session session = request.session(true);
            Boolean usuario = session.attribute("sesion");
            attributes.put("user",(session.attribute("currentUser")==null)?new Usuario("","","",false,false):((Usuario) session.attribute("currentUser")));

            int pagina = 1;

            Boolean admin =session.attribute("admin");

            attributes.put("sesion","false");

            if(admin!=null) {
                if(admin)
                    attributes.put("sesion","true");
            }
            else
            {
                if(usuario!=null){
                    if(usuario)
                        attributes.put("sesion","true");
                }
                else
                    attributes.put("estado","fuera");
            }

            List<Articulo> articulos = paginacion(ArticuloQueries.getInstancia().findAllSorted(),pagina);
            attributes.put("articulos",articulos);

            //paginacion

            int [] paginas = new int [(int)getCantPag(ArticuloQueries.getInstancia().findAllSorted().size())];
            for(int i = 1 ;i <= paginas.length;i++)
            {
                if(pagina== i)
                    continue;
                paginas[i-1]= i;
            }

            attributes.put("irAdelante","si");
            attributes.put("paginaActual","1");

            attributes.put("paginas",paginas);
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        get("/page/:pagina", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Session session = request.session(true);
            Boolean usuario = session.attribute("sesion");
            attributes.put("user",(session.attribute("currentUser")==null)?new Usuario("","","",false,false):((Usuario) session.attribute("currentUser")));

            int pagina = Integer.valueOf(request.params("pagina"));

            Boolean admin =session.attribute("admin");
            attributes.put("sesion","false");

            if(admin!=null) {
                if(admin) {
                       attributes.put("greetings","Saludos Administardor.");
                    attributes.put("sesion","true");
                }
            }
            else {
                if(usuario!=null){
                    if(usuario)
                        attributes.put("sesion","true");
                }
                else
                    attributes.put("estado","fuera");
            }

            List<Articulo> articulos = paginacion(ArticuloQueries.getInstancia().findAllSorted(),pagina);
            attributes.put("articulos",articulos);

            //paginacion
            if(pagina==0&& getCantPag(ArticuloQueries.getInstancia().findAllSorted().size())>1)
                attributes.put("irAdelante","si");
            else attributes.put("irAdelante","no");

            if(pagina != 0&& pagina==(int)getCantPag(ArticuloQueries.getInstancia().findAllSorted().size()-1))
                attributes.put("irAtras","si");
            else attributes.put("irAtras","no");


            int [] paginas = new int [(int)getCantPag(ArticuloQueries.getInstancia().findAllSorted().size())];
            for(int i = 1 ;i <= paginas.length;i++)
            {
                if(pagina== i)
                    continue;
                paginas[i-1]= i;
            }

            attributes.put("paginaActual",Integer.toString(pagina));

            attributes.put("paginas",paginas);
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);


        post("/", (request, response) -> {
            Session sesion = request.session(true);
            Map<String, Object> attributes = new HashMap<>();

            attributes.put("sesion","true");
            attributes.put("user",(sesion.attribute("currentUser")==null)?new Usuario("","","",false,false):((Usuario) sesion.attribute("currentUser")));

            int pagina = 1;


            String insertArt = request.queryParams("crearArt");
            String elimArt = request.queryParams("eliminarArt");

            if(insertArt != null) {
                String titulo = request.queryParams("titulo");
                String texto = request.queryParams("area-articulo");
                String etiquetas = request.queryParams("area-etiqueta");
                ArrayList<Etiqueta> etiq = new ArrayList<Etiqueta>();
                for (String eti : etiquetas.split(",")) {
                   // etiq.add(new Etiqueta(0, eti));
                    EtiquetaQueries.getInstancia().crear(new Etiqueta(eti));
                }
                Usuario user =sesion.attribute("currentUser");
                Articulo art = new Articulo( titulo, texto, sesion.attribute("currentUser"), null, null, etiq);
                ArticuloQueries.getInstancia().crear(art);

            }
            else {
                if (elimArt != null)
                {
                    int elim = Integer.parseInt(request.queryParams("elim"));

                    //System.out.println(elim);
                //    bd.eliminarArticulo(elim);
                }
            }
            List<Articulo> articulos = paginacion((List<Articulo>)ArticuloQueries.getInstancia().findAllSorted(),pagina);
            attributes.put("articulos",articulos);


            //paginacion

            int [] paginas = new int [(int)getCantPag(ArticuloQueries.getInstancia().findAllSorted().size())];
            for(int i = 1 ;i <= paginas.length;i++)
            {
                if(pagina== i)
                    continue;
                paginas[i-1]= i;
            }

            attributes.put("irAdelante","si");
            attributes.put("paginaActual","1");
            attributes.put("paginas",paginas);

            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        get("/articulos", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Session sesion = request.session(true);

            attributes.put("sesion",(sesion.attribute("sesion")==null)?"false":sesion.attribute("sesion").toString());

            attributes.put("user",(sesion.attribute("currentUser")==null)?new Usuario("","","",false,false):((Usuario) sesion.attribute("currentUser")));

            Long id = Long.valueOf(request.queryParams("id"));

            Articulo articulo = ArticuloQueries.getInstancia().find(id);
            attributes.put("comentarios",articulo.getListaComentario());
            attributes.put("articulo",articulo);
            attributes.put("id",request.queryParams("id"));
            attributes.put("etiquetas",articulo.getListaEtiqueta());


            return new ModelAndView(attributes, "articulo.ftl");
        }, freeMarkerEngine);

        post("/articulos", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Session sesion = request.session(true);
            attributes.put("sesion","true");

            attributes.put("user",(sesion.attribute("currentUser")==null)?"false":((Usuario) sesion.attribute("currentUser")));

            String editarArt = null;
            editarArt = (request.queryParams("editarArt")==null)?"null": "nonull";
            String elimC = request.queryParams("eliminarComentario");
            String comen = request.queryParams("comentario");
            Long id = Long.valueOf(request.queryParams("idArticulo"));
            //System.out.println("holaaaerrror");


            if(editarArt.equals("nonull")) {
                String titulo = request.queryParams("titulo");
                String texto = request.queryParams("area-articulo");
                String etiquetas = request.queryParams("area-etiqueta");
                int idArt = Integer.parseInt(request.queryParams("idArt"));
                ArrayList<Etiqueta> etiq = new ArrayList<Etiqueta>();
                for (String eti : etiquetas.split(",")) {
                    etiq.add(new Etiqueta(eti));
                    //System.out.println(eti);
                }
                Articulo art = new Articulo( titulo, texto, sesion.attribute("currentUser"), null, null, etiq);
                //System.out.println(art.getId()+ " "+art.getTitulo());
             //   bd.actualizarArticulo(art);
            }
            else{

                if(elimC!=null)
                {
                    ComentarioQueries.getInstancia().eliminar(Long.valueOf(request.queryParams("eliminarComentarioV")));
                }
                else {
                    if (comen != null || !comen.equals("")) {
                        Comentario com = new Comentario(comen, ((Usuario)sesion.attribute("currentUser")), ((Articulo)ArticuloQueries.getInstancia().find(id)));
                        ComentarioQueries.getInstancia().crear(com);

                    }
                }
            }

            Articulo articulo = ArticuloQueries.getInstancia().find(id);
            attributes.put("comentarios",articulo.getListaComentario());
            attributes.put("articulo",articulo);
            attributes.put("id",id);
            attributes.put("etiquetas",articulo.getListaEtiqueta());

            return new ModelAndView(attributes, "articulo.ftl");
        }, freeMarkerEngine);

        get("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            return new ModelAndView(attributes, "login.ftl");
        }, freeMarkerEngine);

        post("/validacion", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Session session=request.session(true);

            if(session.attribute("sesion"))
            {
                Usuario u= UsuarioQueries.getInstancia().find(request.queryParams("user"));

                attributes.put("message","Bienvenido " + u.getNombre());
                attributes.put("redireccionar", "si");
            }
            else
            {
                attributes.put("message", "Username o password incorrectos.");
                attributes.put("redireccionar", "no");
            }

            //response.redirect("/zonaadmin/");
            return new ModelAndView(attributes, "validacion.ftl");
        }, freeMarkerEngine);

        get("/administrarUsuarios", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("usuarios",UsuarioQueries.getInstancia().findAll());

            return new ModelAndView(attributes, "administrarUsuarios.ftl");
        }, freeMarkerEngine);

        post("/administrarUsuarios", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            if(request.queryParams("elim")!=null)
            {
                String usernam = request.queryParams("elim");
                UsuarioQueries.getInstancia().eliminar(new Usuario(usernam,"","",false, false));
            }
            else
            {

                String user = request.queryParams("user");
                String nombre = request.queryParams("nombre");
                String pass = request.queryParams("pass");
                Boolean admin = (request.queryParams("admin") ==null)? false:true;

                Usuario usuario = new Usuario(user,nombre,pass,admin,true);
                UsuarioQueries.getInstancia().crear(usuario);
            }

            attributes.put("usuarios",UsuarioQueries.getInstancia().findAll());

            return new ModelAndView(attributes, "administrarUsuarios.ftl");
        }, freeMarkerEngine);

        before("/validacion",(request, response) -> {
            Session session=request.session(true);

            String user = request.queryParams("user");
            String pass = request.queryParams("pass");
            Usuario comprobar = UsuarioQueries.getInstancia().find(user);
            if(comprobar!=null) {
                if (comprobar.getPassword().equals(pass)) {
                    session.attribute("sesion", true);
                    session.attribute("currentUser", comprobar);
                }
            }
            else
                session.attribute("sesion", false);
            //response.redirect("/zonaadmin/");

        });

        before("/page/:pagina",(request, response) -> {

            int pagina = Integer.valueOf(request.params(":pagina"));
            int max = (int)getCantPag(ArticuloQueries.getInstancia().findAllSorted().size());
            if(pagina<1)
                response.redirect("/");
            else
                if(pagina> max)
                    response.redirect("/page/"+max);
        });
        get("/clear", (request, response) -> {
            request.session().removeAttribute("sesion");
            request.session().removeAttribute("currentUser");
            response.redirect("/");
            return null;
        });

    }
    public static List<Articulo> paginacion(List<Articulo> la, int pagina)
    {
        List<Articulo> articulosPagina = new ArrayList<>();
        double cant_pags = getCantPag(la.size());
        int rate = 5 *(pagina-1);
        for(int i=  rate; i < rate+5 && i< la.size(); i++ )
        {
            articulosPagina.add(la.get(i));
        }
        return articulosPagina;
    }

    public static double getCantPag(int size)
    {
        return java.lang.Math.ceil(  ((double)size)/ 5 );
    }
}