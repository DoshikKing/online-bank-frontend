import React from 'react';
import { authenticationService } from '../../service/authenticationService';


class Login extends React.Component {
    constructor(props) {
        super(props);

        authenticationService.logout();

        this.state = {
            username: '',
            password: '',
            submitted: false,
            loading: false,
            error: ''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e) {
        const {name, value} = e.target;
        this.setState({[name]: value});
    }

    handleSubmit(e) {
        e.preventDefault();

        this.setState({submitted: true});
        const {username, password} = this.state;

        // stop here if form is invalid
        if (!(username && password)) {
            return;
        }

        this.setState({loading: true});
        authenticationService.login(username, password)
            .then(
                user => {
                    const {from} = this.props.location.state || {from: {pathname: "/"}};
                    this.props.history.push(from);
                },
                error => this.setState({error, loading: false})
            );
    }
    render() {
        const { username, password, submitted, loading, error} = this.state;
        return (<div className="container text-center">
            <div className="back">
                <form onSubmit={this.handleSubmit}>
                    <img className="mb-4" src="android-chrome-512x512.png" alt="" width="57" height="57"/>
                    <h1 className="h3 mb-3 fw-normal">Вход</h1>

                    <div className={'form-floating form-group' + (submitted && !username ? ' has-error' : '')}>

                        <input type="text" name="username" className="form-control" id="floatingInput" placeholder="+7967059****" value={username} onChange={this.handleChange}/>
                        {submitted && !username &&
                            <div className="help-block">Логин необходим для входа!</div>
                        }
                        <label htmlFor="floatingInput">Логин</label>
                    </div>
                    <div className={'mt-3 form-floating form-group' + (submitted && !password ? ' has-error' : '')}>
                        <input type="password" name="password" className="form-control" id="floatingPassword" placeholder="Password" value={password} onChange={this.handleChange}/>
                        {submitted && !password &&
                            <div className="help-block">Пароль необходим для входа!</div>
                        }
                        <label htmlFor="floatingPassword">Пароль</label>
                    </div>
                    <div className="form-group">
                        <button className="mt-3 w-100 btn btn-lg btn-primary" type="submit" disabled={loading}>Войти</button>
                        {loading}
                    </div>
                    {error &&
                        <div className={'alert alert-danger'}>{error}</div>
                    }
                    <p className="mt-5 mb-3 text-muted">&copy; 2022</p>
                </form>
            </div>
        </div>)
    }

}
export default Login;