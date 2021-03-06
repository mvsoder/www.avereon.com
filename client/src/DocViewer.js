import React from "react";

export default class DocViewer extends React.Component {

	state = {
		docIndex: '',
		docContent: ''
	};

	componentDidMount() {
		// Load the viewer content
		fetch(this.props.doc).then((response) => {
			response.text().then((text) => {
				const xml = new DOMParser().parseFromString(text, "text/xml");
				this.setState({docContent: text, docIndex: this.extractDocIndex(xml, this.props.outline)});
			})
		});
	}

	extractDocIndex(xml, level) {
		let idx = "<div>";

		const nodes = xml.children[0].children;
		for (let index in nodes) {
			let node = nodes[index];
			let tag = node.tagName;
			let id = node.id;
			let text = node.textContent;
			if (tag && tag.length === 2 && tag.startsWith("h")) {
				let depth = tag.charAt(1);
				//console.log(tag + "#" + id + "=" + text);
				if (depth <= level) {
					if (id) idx += "<a href=\"#" + id + "\">";
					idx += "<h" + depth + ">";
					idx += text;
					idx += "</h" + depth + ">";
					if (id) idx += "</a>";
					idx += "\n"
				}
			}
		}
		idx += "</div>";

		return idx;
	}

	componentDidUpdate() {
		if (window.location.hash) document.getElementById(window.location.hash.substr(1)).scrollIntoView();
	}

	getDocIndex() {
		return {__html: this.state.docIndex}
	};

	getDocContent() {
		return {__html: this.state.docContent};
	}

	render() {
		return (
			<div className='content'>
				<div className='doc-view'>
					<div className='doc-index' dangerouslySetInnerHTML={this.getDocIndex()}/>
					<div className='doc-content' dangerouslySetInnerHTML={this.getDocContent()}/>
				</div>
			</div>
		)
	}

}
