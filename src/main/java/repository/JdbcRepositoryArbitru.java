package repository;

import domain.Arbitru;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcRepositoryArbitru implements IRepository<String, Arbitru> {
    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public JdbcRepositoryArbitru(Properties props) {
        logger.info("Initializing DBRepo with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size()  {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Arbitri")) {
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
    public Arbitru save(Arbitru entity) {
        logger.traceEntry("saving arbitru {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Arbitri values (?,?)")){
            preStmt.setString(1,entity.getName());
            preStmt.setString(2,entity.getPassword());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public void delete(String s) {
        logger.traceEntry("deleting arbitru with name: {}",s);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Arbitru where name=?")){
            preStmt.setString(1,s);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(String s, Arbitru entity) {

    }

    @Override
    public Arbitru findOne(String s) {
        logger.traceEntry("finding task with name: {} ",s);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Arbitri where name=?")){
            preStmt.setString(1,s);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    String name = result.getString("name");
                    String password = result.getString("password");
                    Arbitru arbitru = new Arbitru(name,password);
                    logger.traceExit(arbitru);
                    return arbitru;
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
    public Iterable<Arbitru> findAll() {
        //logger.();
        Connection con=dbUtils.getConnection();
        List<Arbitru> arbitri=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Arbitri")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    String name = result.getString("name");
                    String password = result.getString("password");
                    Arbitru arbitru = new Arbitru(name,password);
                    arbitri.add(arbitru);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(arbitri);
        return arbitri;
    }

}
