import { NavLink } from 'react-router-dom';
import { hasAnyRoles } from 'util/auth';

import './styles.css';

const Navbar = () => {
  return (
    <nav className="admin-nav-container">
      <ul>
        <li>
          <NavLink to="/admin/products" className="admin-nav-item">
            Produtos
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/categories" className="admin-nav-item">
            Categorias
          </NavLink>
        </li>
        {hasAnyRoles(['ROLE_ADMIN']) && (
          <li>
            <NavLink to="/admin/users" className="admin-nav-item">
              Usuarios
            </NavLink>
          </li>
        )}
      </ul>
    </nav>
  );
};

export default Navbar;
