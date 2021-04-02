import React from "react";
import { StyleSheet, View, Text } from "react-native";

export default function HelloWorld() {
    return (
        <View style={styles.container}>
            <Text style={{color: "red"}}></Text>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        height: 100,
        width: 100,
        justifyContent: "center",
        backgroundColor: "#20232a",
    },
});
