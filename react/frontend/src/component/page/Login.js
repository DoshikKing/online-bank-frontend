export default function Login() {
    return(
        <div className="container text-center">
            <div className="back">
                <form>
                    <img className="mb-4" src="android-chrome-512x512.png" alt="" width="57" height="57"/>
                    <h1 className="h3 mb-3 fw-normal">Please sign in</h1>

                    <div className="form-floating">
                        <input type="email" className="form-control" id="floatingInput" placeholder="name@example.com"/>
                        <label htmlFor="floatingInput">Email address</label>
                    </div>
                    <div className="mt-3 form-floating">
                        <input type="password" className="form-control" id="floatingPassword" placeholder="Password"/>
                        <label htmlFor="floatingPassword">Password</label>
                    </div>
                    <button className="mt-3 w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
                    <p className="mt-5 mb-3 text-muted">&copy; 2022</p>
                </form>
            </div>
        </div>
    );
}