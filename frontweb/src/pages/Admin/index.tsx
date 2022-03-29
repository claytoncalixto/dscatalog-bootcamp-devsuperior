import PrivateRoute from 'components/PrivateRoute';
import { Switch} from 'react-router-dom';
import Navbar from './Navbar';
import Products from './Products';
import Users from './Users';

import './styles.css';


const Admin = () => {
  return (
    <div className="admin-conatiner">
      <Navbar />
      <div className="admin-content">
        <Switch>
            <PrivateRoute path="/admin/products">
                <Products />
            </PrivateRoute>
            <PrivateRoute path="/admin/categories">
                <h1>Category CRUD</h1>
            </PrivateRoute>
            <PrivateRoute path="/admin/users" roles={['ROLE_ADMIN']}>
                <Users />
            </PrivateRoute>
        </Switch>
      </div>
    </div>
  );
};

export default Admin;
