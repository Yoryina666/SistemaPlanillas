package Model;

/**
 * @author Aylan Miranda
 */
public enum TipoUsuario {
    ADMINISTRADOR {
        @Override
        public String toString() {
            return "Administrador";
        }
    }, PLANILLERO {
        @Override
        public String toString() {
            return "Planillero";
        }
    }, RECURSOS_HUMANOS {
        @Override
        public String toString() {
            return "Recursos Humanos";
        }
    }
}
