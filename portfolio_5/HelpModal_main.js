import React, {Component} from 'react';
import {
  Text, View, Image, Alert, Dimensions, Platform
} from 'react-native';
import Modal from 'react-native-modalbox';
import Button from 'react-native-button';

var screen = Dimensions.get('window');
export default class HelpModal extends Component{
  constructor(props){
    super(props);
  }
  showHelpModal = () => {
    this.refs.mainHelp.open();
  }
  render(){
    return(
      <Modal
        ref={"mainHelp"}
        style={{
          justifyContent: 'center',
          borderRadius: Platform.OS==='ios'? 30:0,
          shadowRadius: 7,
          width: screen.width-80,
          height: 280
        }}
        position='center'
        backdrop={true}
     /*   onClosed={()=> {
          alert("Modal Closed");
        }} */
      >
        <Text style={{
          fontSize: 30,
          fontWeight: 'bold',
          textAlign:'center',
          marginTop: -20
        }}>Information</Text>
        <Text style={{
          fontSize: 20,
          marginLeft: 20,
          marginTop: 30
        }}>
        ■ 필름 구매 안내 : (주소)</Text>
        <Text style={{
          fontSize: 20,
          marginLeft: 20,
          marginTop: 10
        }}>
        ■ 촬영 방법 : (주소)</Text>
        <Text style={{
          fontSize: 20,
          marginLeft: 20,
          marginTop: 10
        }}>
        ■ 기록하는 방법 : (주소)</Text>
      </Modal>
    );
  }
}