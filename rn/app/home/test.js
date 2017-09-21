import React, { Component } from 'react';

console.ignoredYellowBox = ['Warning: Failed propType: SceneView'];
import {
    Scene,
    Router,
    Actions,
    Reducer,
    ActionConst,
    Overlay,
    Tabs,
    Modal,
    Drawer,
    Stack,
    Lightbox,
} from 'react-native-router-flux';
import Register from '../components/Register';
import Home from '../components/Home';
export default class FlyabbitForRN extends Component {
    render() {
        return (
            <Router>
                <Scene key="root">
                    <Scene key="register" component={Register} title="Register"/>
                    <Scene key="home" component={Home}/>
                </Scene>
            </Router>
        );
    }
}
