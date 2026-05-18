package model;

/**
 * Clase base Usuario.
 * Representa un usuario del sistema (puede ser Jugador o Administrador).
 */
public class Usuario {

    protected String username;
    protected String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

	public Usuario(){
		
	}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Comprueba si las credenciales son correctas.
     */
    public boolean autenticar(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return "Usuario{username='" + username + "'}";
    }
}