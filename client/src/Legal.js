import React from "react";

export default class Legal extends React.Component {

	render() {
		return (
			<div className='content'>
				<h1 className='title'>Legal Notices</h1>

				<div>
				<h2>Copyright</h2>
				<p>
					Copyright &copy; 2019 Avereon
				</p>

				<h2>Trademarks</h2>
				<p>
					Java and OpenJDK are trademarks or registered trademarks of Oracle
					and/or its affiliates. Other names may be trademarks of their
					respective owners.
				</p>

				<h2>Licenses</h2>
				<p>
					<a href='/licenses/mit'>MIT License</a>
				</p>
				</div>
			</div>
		)
	}

}