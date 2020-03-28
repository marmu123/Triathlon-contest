package repository;

import domain.Proba;
import domain.Rezultat;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.TipProba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcRepositoryRezultat implements FilterRepositoryRezultat {
    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public JdbcRepositoryRezultat(Properties props) {
        logger.info("Initializing DBRepo with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size()  {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Rezultate")) {
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
    public Rezultat save(Rezultat entity) {
        logger.traceEntry("saving rezultat {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Rezultate values (?,?,?)")){
            preStmt.setString(1,entity.getNumeParticipant());
            preStmt.setInt(2,entity.getIdProba());
            preStmt.setDouble(3,entity.getNumarPuncte());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public void delete(Pair<Integer,String> id) {
        logger.traceEntry("deleting rezultat with id: {}",id);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Rezultate where idParticipant=? and idProba=?")){
            preStmt.setInt(2,id.getKey());
            preStmt.setString(1,id.getValue());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Pair<Integer,String> id, Rezultat entity) {
        //todo
    }

    @Override
    public Rezultat findOne(Pair<Integer,String> id) {
        logger.traceEntry("finding rezultat with id: {} ",id);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Rezultate where idProba=? and idParticipant=?")){
            preStmt.setInt(1,id.getKey());
            preStmt.setString(2,id.getValue());
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int idProba = result.getInt("idProba");
                    String idParticipant = result.getString("idParticipant");
                    Double numarPuncte=result.getDouble("numarPuncte");
                    Rezultat rezultat = new Rezultat(idProba,idParticipant,numarPuncte);
                    logger.traceExit(rezultat);
                    return rezultat;
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
    public Iterable<Rezultat> findAll() {
        //logger.();
        Connection con=dbUtils.getConnection();
        List<Rezultat> rezultate=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Rezultate")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int idProba = result.getInt("idProba");
                    String idParticipant = result.getString("idParticipant");
                    Double numarPuncte=result.getDouble("numarPuncte");
                    Rezultat rezultat = new Rezultat(idProba,idParticipant,numarPuncte);
                    rezultate.add(rezultat);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(rezultate);
        return rezultate;
    }

    @Override
    public double getTotalScoreForAParticipant(String participant) {
        //logger.();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select SUM(numarPuncte) as NRPCT  from Rezultate where idParticipant=?")) {
            preStmt.setString(1,participant);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getDouble("NRPCT"));
                    return result.getDouble("NRPCT");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return 0;
    }

    @Override
    public double getScoreOfAParticipantForAProbe(String participant, TipProba tipProba){
        //logger.();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select numarPuncte from Rezultate " +
                "inner join Probe P on Rezultate.idProba = P.id " +
                "where Rezultate.idParticipant=? and P.tipProba=?")) {
            preStmt.setString(1,participant);
            preStmt.setString(2,tipProba.toString());
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getDouble("numarPuncte"));
                    return result.getDouble("numarPuncte");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return 0;
    }
}
