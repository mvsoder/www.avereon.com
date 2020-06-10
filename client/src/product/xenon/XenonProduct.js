import React from 'react';
import * as Icon from "../../Icon";
import {Platform} from "../../Platform";
import {library} from '@fortawesome/fontawesome-svg-core';
import {fas} from '@fortawesome/free-solid-svg-icons'
import {fab} from '@fortawesome/free-brands-svg-icons'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import '../../css/product.css';

const ROOT_URL = "https://www.avereon.com/download";

library.add(fas, fab);

function productCards(product, success, failure) {
	const url = ROOT_URL + "/product/cards/" + product;
	return fetch(url)
		.then((response) => response.status === 200 ? response.json() : {})
		.then((card) => success(card))
		.catch(failure)
}

export default class XenonProduct extends React.Component {

	state = {
		stable: {
			linux: {},
			macosx: {},
			windows: {}
		},
		latest: {
			linux: {},
			macosx: {},
			windows: {}
		}
	};

	componentDidMount() {
		productCards("xenon", (cards) => {
			console.log("cards=" + JSON.stringify(cards));
			this.setState(cards);
		});
		// productCard("stable", "linux", (card) => {
		// 	console.log("card=" + JSON.stringify(card));
		// 	this.setState({stable: {...this.state.stable, linux: {...card}}});
		// });
		// productCard("stable", "macosx", (card) => {
		// 	console.log("card=" + JSON.stringify(card));
		// 	this.setState({stable: {...this.state.stable, macosx: {...card}}});
		// });
		// productCard("stable", "windows", (card) => {
		// 	console.log("card=" + JSON.stringify(card));
		// 	this.setState({stable: {...this.state.stable, windows: {...card}}});
		// });
		// productCard("latest", "linux", (card) => {
		// 	console.log("card=" + JSON.stringify(card));
		// 	this.setState({latest: {...this.state.latest, linux: {...card}}});
		// });
		// productCard("latest", "macosx", (card) => {
		// 	console.log("card=" + JSON.stringify(card));
		// 	this.setState({latest: {...this.state.latest, macosx: {...card}}});
		// });
		// productCard("latest", "windows", (card) => {
		// 	console.log("card=" + JSON.stringify(card));
		// 	this.setState({latest: {...this.state.latest, windows: {...card}}});
		// });
	}

	static createDownloadTile(type, category, product, platform, card) {
		const artifact = card.artifact;
		const version = card.version;

		let style = "download " + type;
		const platformSize = type === "primary" ? '3x' : '2x';
		const platformIcon = type === "primary" ? 'download' : ['fab', platform.ICON];
		if (version === undefined) {
			return <div className={style + " disabled"}>
				<div className='download-layout'>
					<FontAwesomeIcon className="download-icon" icon={platformIcon} size={platformSize}/>
					<div className='download-metadata'>
						<div className='title'>{product} for {platform.NAME}</div>
						<div>unavailable</div>
					</div>
				</div>
			</div>
		} else {
			return <a className={style + " " + category} href={'https://www.avereon.com/download/' + category + '/' + artifact + '/' + platform.KEY + '/install/jar'}>
				<div className='download-layout'>
					<FontAwesomeIcon className="download-icon" icon={platformIcon} size={platformSize}/>
					<div className='download-metadata'>
						<div className='title'>{product} for {platform.NAME}</div>
						<div>{version}</div>
					</div>
				</div>
			</a>
		}
	}

	render() {
		let stableDownload = <div className='download-row'>
			{XenonProduct.createDownloadTile("primary", "stable", "Xenon", Platform, this.state.stable[Platform.KEY])}
		</div>;

		let stableDownloads = <div className='download-row'>
			{XenonProduct.createDownloadTile("secondary", "stable", "Xenon", Platform.LINUX, this.state.stable[Platform.LINUX.KEY])}
			{XenonProduct.createDownloadTile("secondary", "stable", "Xenon", Platform.MAC, this.state.stable[Platform.MAC.KEY])}
			{XenonProduct.createDownloadTile("secondary", "stable", "Xenon", Platform.WINDOWS, this.state.stable[Platform.WINDOWS.KEY])}
		</div>;

		let latestDownloads = <div className='download-row'>
			{XenonProduct.createDownloadTile("secondary", "latest", "Xenon", Platform.LINUX, this.state.latest[Platform.LINUX.KEY])}
			{XenonProduct.createDownloadTile("secondary", "latest", "Xenon", Platform.MAC, this.state.latest[Platform.MAC.KEY])}
			{XenonProduct.createDownloadTile("secondary", "latest", "Xenon", Platform.WINDOWS, this.state.latest[Platform.WINDOWS.KEY])}
		</div>;

		return (
			<div className='content'>
				<div className='product'>

					<div className='product-title'>
						<img className="product-icon" alt="" src={Icon.XENON_LIGHT}/>
						<div className='product-name'>Xenon</div>
					</div>

					<div className='product-content'>
						Xenon is an application framework that provides common services for
						modern desktop applications. Specific functionality is implemented
						with downloadable packages called mods. Users are encouraged to
						discover and utilize the mods that best suit their needs.
					</div>

						{stableDownload}

					<div className='resource-row'>
						<div className='resource-tile'>
							<div><a href="/product/xenon/docs/user-guide"><FontAwesomeIcon icon={['fas', 'user']}/> User Guide</a></div>
							<div><a href="/product/xenon/mods"><FontAwesomeIcon icon={['fas', 'cubes']}/> Mods</a></div>
							<div><a href={process.env.PUBLIC_URL + '/product/xenon/docs/api/index.html'}><FontAwesomeIcon icon={['fas', 'tools']}/> Xenon API</a></div>
							<div><a href='https://github.com/avereon/xenon' target="_blank" rel="noopener noreferrer"><FontAwesomeIcon icon={['fab', 'github']}/> Source Code</a></div>
						</div>
						<div className='resource-tile'>
							<div><a href="/product/xenon/docs/mods-guide"><FontAwesomeIcon icon={['fas', 'file']}/> Building Mods</a></div>
							<div><a href="/product/xenon/docs/tool-guide"><FontAwesomeIcon icon={['fas', 'file']}/> Building Tools</a></div>
						</div>
					</div>

					<h3>Other Platforms</h3>
					{stableDownloads}
					<h3>Development Builds</h3>
					{latestDownloads}

				</div>
			</div>
		);
	}

}
