const handleLogin = async (e) => {
  e.preventDefault();
  const res = await fetch('http://localhost:8080/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: `username=${username}&password=${password}`,
    credentials: 'include'   // important for session cookie
  });

  if (res.ok) {
    navigate('/home');
  }
};