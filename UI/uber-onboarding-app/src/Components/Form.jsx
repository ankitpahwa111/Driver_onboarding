import React from 'react'
import { Grid, Paper, Avatar, Typography, TextField, Button, Alert } from '@mui/material'
import Snackbar from '@mui/material/Snackbar';
import LocalTaxiIcon from '@mui/icons-material/LocalTaxi';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import Checkbox from '@mui/material/Checkbox';
import { Formik, Field, Form, ErrorMessage } from 'formik'
import MenuItem from '@mui/material/MenuItem';
import { FormHelperText } from '@mui/material'
import * as Yup from 'yup'
import axios from 'axios';

// const Alert = React.forwardRef(function Alert(props, ref) {
//     return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
// });


const Signup = () => {
    const paperStyle = { padding: 20, width: 800, margin: "0 auto" }
    const headerStyle = { margin: 0 }
    const avatarStyle = { backgroundColor: 'black' }
    const marginTop = { marginTop: 10 }
    const initialValues = {
        name: '',
        email: '',
        gender: '',
        phoneNumber: '',
        password: '',
        confirmPassword: '',
        termsAndConditions: false
    }
    const validationSchema = Yup.object().shape({
        name: Yup.string().min(3, "It's too short").required("Required"),
        email: Yup.string().email("Enter valid email").required("Required"),
        gender: Yup.string().oneOf(["male", "female"], "Required").required("Required"),
        phoneNumber: Yup.number().typeError("Enter valid Phone Number").required('Required'),
        age: Yup.number().typeError("Age should be greater than 18").required('Required').moreThan(18),
        password: Yup.string().min(8, "Password minimum length should be 8").required("Required"),
        confirmPassword: Yup.string().oneOf([Yup.ref('password')], "Password not matched").required("Required"),
        termsAndConditions: Yup.string().oneOf(["true"], "Accept terms & conditions")
    })

    const [open, setOpen] = React.useState(false);
    const [snackbarMessage, setSnackBarMessage] = React.useState("")


    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        setOpen(false);
    };

    const onSubmit = (values, props) => {
        console.log(values)
        console.log(props)
        let data = JSON.stringify({
            "userType": "DRIVER",
            "name": values['name'],
            "contact": values['phoneNumber'],
            "password": values['password'],
            "age": values['age'],
            "address": values['address'],
            "identityType": "DRIVING_LICENCE",
            "identityNumber": values['identity']
        });

        let config = {
            method: 'post',
            maxBodyLength: Infinity,
            url: 'http://localhost:8080/api/user',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            data: data
        };

        axios.request(config)
            .then((res) => {
                props.setSubmitting(false)

                console.log(res)
                props.setSubmitting(false)
                if (res.status === 200) setOpen(true)
                const val = res.data ? (res.data.message ? res.data.message : res.data.httpStatus) : res.status
                setSnackBarMessage(val)
            })
            .catch((error) => {
                console.log(error);
            });

    }
    const [age, setAge] = React.useState('');
    let vertical = "top";
    let horizontal = "center";
    return (
        <Grid>
            <Paper style={paperStyle}>
                <Grid align='center'>
                    <Avatar style={avatarStyle}>
                        <LocalTaxiIcon />
                    </Avatar>
                    <h2 style={headerStyle}>Onboard as a Driver Partner with Us</h2>
                    <Typography variant='caption' gutterBottom>Please fill this form to create an account !</Typography>
                </Grid>
                <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={onSubmit}>
                    {(props) => (
                        <Form>

                            <Field as={TextField} fullWidth name="name" label='Full Name'
                                placeholder="Enter your name" style={marginTop} helperText={<ErrorMessage name="name" />} />
                            <Field as={TextField} fullWidth name="email" label='Email'
                                placeholder="Enter your email" helperText={<ErrorMessage name="email" />} style={marginTop} />
                            <FormControl component="fieldset" style={marginTop}>
                                <FormLabel component="legend">Gender</FormLabel>
                                < Field as={RadioGroup} aria-label="gender" name="gender" style={{ display: 'initial' }}>
                                    <FormControlLabel value="female" control={<Radio />} label="Female" />
                                    <FormControlLabel value="male" control={<Radio />} label="Male" />
                                </ Field>
                            </FormControl>
                            <FormHelperText><ErrorMessage name="gender" /></FormHelperText>
                            <Field as={TextField} fullWidth name="phoneNumber" label='Phone Number'
                                placeholder="Enter your phone number" helperText={<ErrorMessage name="phoneNumber" />} style={marginTop} />
                            <Field as={TextField} fullWidth name='address'
                                label='Address' placeholder="Full Address" style={marginTop} />
                            <Field as={TextField} fullWidth name="age" label='Age'
                                placeholder="Enter your age" helperText={<ErrorMessage name="age" />} style={marginTop} />
                            {/* <FormHelperText><ErrorMessage name="age" /></FormHelperText> */}
                            <TextField
                                id="id_type"
                                select
                                label="Identity Type"
                                defaultValue="DRIVING_LICENCE"
                                style={{ width: 200, marginTop: 10 }}
                            // style={marginTop}
                            >
                                <MenuItem key="AADHAR" value="AADHAR">
                                    Aadhaar
                                </MenuItem>
                                <MenuItem key="DRIVING_LICENCE" value="DRIVING_LICENCE">
                                    Driving License
                                </MenuItem>
                                <MenuItem key="PAN" value="PAN">
                                    PAN
                                </MenuItem>
                            </TextField>
                            <Field as={TextField} fullWidth name="identity" label='Identity'
                                placeholder="Enter your ID" style={marginTop} />
                            <Field as={TextField} fullWidth name='password' type="password"
                                label='Password' placeholder="Enter your password"
                                helperText={<ErrorMessage name="password" />} style={marginTop} />
                            {/* <FormHelperText><ErrorMessage name="password" /></FormHelperText> */}
                            <Field as={TextField} fullWidth name="confirmPassword" type="password"
                                label='Confirm Password' placeholder="Confirm your password"
                                helperText={<ErrorMessage name="confirmPassword" />} style={marginTop} />
                            {/* <FormHelperText><ErrorMessage name="confirmPassword" /></FormHelperText> */}
                            <FormControlLabel
                                control={<Field as={Checkbox} name="termsAndConditions" />}
                                label="I accept the terms and conditions."
                                style={marginTop}
                            />
                            <FormHelperText><ErrorMessage name="termsAndConditions" /></FormHelperText>
                            <Button type='submit' variant='contained'
                                disabled={props.isSubmitting}
                                color='primary'>{props.isSubmitting ? "Loading" : "Sign up"}</Button>

                        </Form>
                    )}
                </Formik>
            </Paper>
            <Snackbar
                open={open}
                autoHideDuration={6000}
                onClose={handleClose}
                anchorOrigin={{ vertical, horizontal }}
                key="topcenter"
                style={{ width: 300 }}
            >
                <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
                    {snackbarMessage}
                </Alert>
            </Snackbar>
        </Grid>
    )
}

export default Signup;