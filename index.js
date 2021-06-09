/**
 * Created by juanjimenez on 07/12/2016.
 * Otomogroove ltd 2017
 * 
 * Modified by cmpayc on 10/06/2-21
 */

 "use strict";
 import React, { PureComponent } from "react";
 import { Platform, processColor, DeviceEventEmitter, requireNativeComponent } from "react-native";
 
 import resolveAssetSource from "react-native/Libraries/Image/resolveAssetSource";
 
 type StateType = { componentID: string };
 
 export default class WaveForm extends PureComponent<WaveObjectPropsType, StateType> {
 
   _makeid() {
     var text = "";
     var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
 
     for (var i = 0; i < 5; i++) text += possible.charAt(Math.floor(Math.random() * possible.length));
 
     return text;
   }
 
   componentWillMount() {
     const componentID = this._makeid();
     this.setState({ componentID });
   }
 
   render() {
     const { source } = this.props;
     const { componentID } = this.state;
     const assetResoved = resolveAssetSource(source) || {};
 
     let uri = assetResoved.uri;
     if (uri && uri.match(/^\//)) {
       // uri = `file://${uri}`;`
     }
 
     const isNetwork = !!(uri && uri.match(/^https?:/));
     const isAsset = !!(uri && uri.match(/^(assets-library|file|content|ms-appx|ms-appdata):/));
 
     const nativeProps = {
       ...this.props,
       waveFormStyle: {
         ogWaveColor: processColor(this.props.waveFormStyle.waveColor),
         ogScrubColor: processColor(this.props.waveFormStyle.scrubColor),
         ogBackgroundColor: processColor(this.props.waveFormStyle.backgroundColor),
       },
 
       src: {
         uri,
         isNetwork,
         isAsset,
         type: source.type,
         mainVer: source.mainVer || 0,
         patchVer: source.patchVer || 0
       },
       componentID
     };
 
     return <OGWaverformView {...nativeProps} />;
   }
 }
 
 const OGWaverformView = requireNativeComponent("OGWave", WaveForm);
 