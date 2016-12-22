package de.hdm_stuttgart.huber.itprojekt.server.db;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.sql.*;
import java.util.Vector;


public class UserInfoMapper extends DataMapper {

    //Statisches Attribut, welches den Singleton-NoteUserMapper enthält
    private static UserInfoMapper userInfoMapper = null;

    //Konstruktor (protected, um unauthorisiertes Instanziieren der Klasse zu verhindern)
    protected UserInfoMapper() throws ClassNotFoundException, SQLException {
     
    }

    //Öffentliche statische Methode, um den Singleton-NoteUserMapper zu erhalten
    public static UserInfoMapper getUserInfoMapper() {
        if (userInfoMapper == null) {
        	
            try {
				userInfoMapper = new UserInfoMapper();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
       
        return userInfoMapper;
    }

    //create Methode
    // Use register
    @Deprecated 
    public UserInfo create(UserInfo noteUser) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO userinfo(FirstName, UserName, SurName, Email, GoogleId) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, noteUser.getFirstName());
            stmt.setString(2, noteUser.getNickname());
            stmt.setString(3, noteUser.getSurName());
            stmt.setString(4, noteUser.getEmailAddress());
            stmt.setString(5, noteUser.getGoogleId());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                return findById(rs.getInt(1));
            }   
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
        }
        return noteUser;
    }

    // FINDBYID
    public UserInfo findById(int id) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

      //Was macht die isObjectLoaded Verzweigung hier?
       if (isObjectLoaded(id, UserInfo.class)) {
    	   return (UserInfo) loadedObjects.get(id);
       }
       
       try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM notizbuch.userinfo WHERE id = ?");
            stmt.setInt(1, id);

            //Ergebnis holen
            ResultSet results = stmt.executeQuery();
            if (results.next()) {

                UserInfo noteUser = new UserInfo(results.getInt("id"),
            	        results.getString("firstname"),
                        results.getString("username"),
                        results.getString("lastname"),
                        results.getString("email"),
                        results.getString("google_id"));
                
                loadedObjects.put(results.getInt("id"), noteUser);
                return noteUser;
            }

        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            return null;
        }

        return null;
    }


    //save-Methode: NoteUser-Objekt wird wiederholt in die DB geschrieben
    public UserInfo save(UserInfo noteUser) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE userinfo SET firstName=?, userName=?, surName=?, email=?, googleId=? WHERE id=?");

            stmt.setString(1, noteUser.getFirstName());
            stmt.setString(2, noteUser.getNickname());
            stmt.setString(3, noteUser.getSurName());
            stmt.setString(4, noteUser.getEmailAddress());
            stmt.setString(5, noteUser.getGoogleId());

            stmt.executeUpdate();

        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            throw new IllegalArgumentException();
        }
        return findById(noteUser.getId());
    }


    public void delete(UserInfo noteUser) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM notizbuch.userinfo WHERE id = ?");
            stmt.setLong(1, noteUser.getId());
            stmt.executeUpdate();

        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public Vector<UserInfo> getAllNoteUser() throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        Vector<UserInfo> result = new Vector<>();
        
        try {
        // wieso kein prepared Statement mehr?
        // PreparedStatement stmt = con.prepareStatement("SELECT NoteUserId FROM NoteUser");
        Statement stmt = con.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM notizbuch.userinfo");
        	
       while (results.next()){
    	   UserInfo noteUser = new UserInfo(results.getInt("id"),
    			   results.getString("firstname"),
                   results.getString("username"),
                   results.getString("lastname"),
                   results.getString("email"),
                   results.getString("google_id"));
    	   result.add(noteUser);
       	   }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return result;
    }
    
    public boolean isUserRegistered(String googleId) {
    	
    	try {
    	
    	PreparedStatement ps = connection.prepareStatement("SELECT EXISTS(SELECT * FROM notizbuch.userinfo WHERE google_id = ?) AS does_exist");
    	ps.setString(1, googleId);
    	
    	ResultSet rs = ps.executeQuery();
    	
    	if (rs.next()) {
    		
    		return rs.getBoolean("does_exist");
    	
    	} 
    	
    	} catch (SQLException e) {
    		
    		e.printStackTrace();
    		
    	}
    	
    	return false;
    }
    
    /**
     * Registriert einen User mit seiner GoogleId in der Datenbank
     * Diese Funktion registriert nur username, email und GoogleId um Chaos zu vermeiden
     * Später wird dem User nach der Registrierung angeboten, den Rest auch gleich zu setzen
     * @param u
     */
    public void registerUser(UserInfo u) {
    	
    	try {
    		
			PreparedStatement ps = connection.prepareStatement("INSERT INTO notizbuch.userinfo(username, email, google_id)" + 
					" VALUES (?, ?, ?)");
			
			ps.setString(1, u.getNickname());
			ps.setString(2, u.getEmailAddress());
			ps.setString(3, u.getGoogleId());
						
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    public UserInfo findUserByGoogleId(String googleId) {
    	
    	try {
    		
			PreparedStatement ps = connection.prepareCall("SELECT id FROM notizbuch.userinfo WHERE google_id = ?");
			ps.setString(1, googleId);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				
				return findById(rs.getInt("id"));
				
			} else {
				
				throw new IllegalArgumentException("Logikfehler: Kein User gefunden");
				
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    
}