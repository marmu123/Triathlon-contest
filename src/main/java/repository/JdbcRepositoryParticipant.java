package repository;

import domain.Participant;
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

public class JdbcRepositoryParticipant implements FilterRepositoryParticipant
{
    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public JdbcRepositoryParticipant(Properties props) {
        logger.info("Initializing DBRepo with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size()  {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Participanti")) {
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
    public void save(Participant entity) {
        logger.traceEntry("saving participant {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Participanti values (?)")){
            preStmt.setString(1,entity.getNume());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public void delete(String s) {
        logger.traceEntry("deleting participant with name: {}",s);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Participanti where nume=?")){
            preStmt.setString(1,s);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(String s, Participant entity) {

    }

    @Override
    public Participant findOne(String s) {
        logger.traceEntry("finding task with name: {} ",s);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Participanti where nume=?")){
            preStmt.setString(1,s);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    String nume = result.getString("nume");
                    Participant participant=new Participant(nume);
                    logger.traceExit(participant);
                    return participant;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No arbitru found with name {}", s);

        return null;
    }

    @Override
    public Iterable<Participant> findAll() {
        //logger.();
        Connection con=dbUtils.getConnection();
        List<Participant> participants=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participanti")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    String nume = result.getString("nume");
                    Participant participant=new Participant(nume);
                    participants.add(participant);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(participants);
        return participants;
    }

    @Override
    public Iterable<Participant> findAllForAProbe(TipProba tipProba){
        //logger.();
        Connection con=dbUtils.getConnection();
        List<Participant> participants=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participanti p" +
                " inner join Rezultate R on p.nume = R.idParticipant" +
                " inner join Probe P2 on R.idProba = P2.id" +
                " where P2.tipProba=?")) {
            preStmt.setString(1,tipProba.toString());
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    String nume = result.getString("nume");
                    Participant participant=new Participant(nume);
                    participants.add(participant);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(participants);
        return participants;
    }
}

