package repository;

import domain.Arbitru;
import domain.Proba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import utils.TipProba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
@Component
public class JdbcRepositoryProba implements IRepository<Integer, Proba> {
    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public JdbcRepositoryProba(Properties props) {
        logger.info("Initializing DBRepo with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size()  {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Probe")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public Proba save(Proba entity) {
        logger.traceEntry("saving proba {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Probe(numeArbitru, tipProba) values (?,?)")){
            preStmt.setString(1,entity.getNumeArbitru());
            preStmt.setString(2,entity.getTipProba().toString());
            int result=preStmt.executeUpdate();

        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }


        try(PreparedStatement preStmt=con.prepareStatement("select * from Probe  order by id desc limit 1")){
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    return new Proba(result.getInt("id"), result.getString("numeArbitru"), TipProba.valueOf(result.getString("tipProba")));
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public void delete(Integer id) {
        logger.traceEntry("deleting proba with id: {}",id);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Probe where id=?")){
            preStmt.setInt(1,id);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Proba update(Integer id, Proba entity) {
        if(findOne(id)!=null) {
            logger.traceEntry("updating proba with id: {}", id);
            Connection con = dbUtils.getConnection();
            try (PreparedStatement preStmt = con.prepareStatement("update Probe set numeArbitru=?,tipProba=? where id=?")) {
                preStmt.setInt(3, id);
                preStmt.setString(1, entity.getNumeArbitru());
                preStmt.setString(2, entity.getTipProba().toString());
                int result = preStmt.executeUpdate();
                return new Proba(id,entity.getNumeArbitru(),entity.getTipProba());
            } catch (SQLException ex) {
                logger.error(ex);
                System.out.println("Error DB " + ex);
            }
            logger.traceExit();
        }
        else
           return save(new Proba(id,entity.getNumeArbitru(),entity.getTipProba()));
        return null;
    }

    @Override
    public Proba findOne(Integer id) {
        logger.traceEntry("finding proba with id: {} ",id);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Probe where id=?")){
            preStmt.setInt(1,id);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int idd = result.getInt("id");
                    String numeArbitru = result.getString("numeArbitru");
                    String tipProba=result.getString("tipProba");
                    Proba proba = new Proba(idd,numeArbitru, TipProba.valueOf(tipProba));
                    logger.traceExit(proba);
                    return proba;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No arbitru found with name {}", id);

        return null;
    }

    @Override
    public Iterable<Proba> findAll() {
        //logger.();
        Connection con=dbUtils.getConnection();
        List<Proba> probe=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Probe")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int idd = result.getInt("id");
                    String numeArbitru = result.getString("numeArbitru");
                    String tipProba=result.getString("tipProba");
                    Proba proba = new Proba(idd,numeArbitru,TipProba.valueOf(tipProba));
                    probe.add(proba);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(probe);
        return probe;
    }

}
