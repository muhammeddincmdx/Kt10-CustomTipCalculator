## finale view

![resim](https://github.com/muhammeddincmdx/Kt10-CustomTipCalculator/assets/54439858/51b35807-ad47-4461-a8fb-e93dd54ed773)


### önemli kod parçaları ve notlar


bu uygulamada kullanıcıya numara klavyesi gösterme enter ile diğer boşluğa geçme uygulamaları yapıldı

switch kullanımı kullanıcı girdilerinin mutablestate ile tutulması 

````


@Composable
fun TipCalculateApp(){

    //1
    var amountInput by remember { mutableStateOf("") }
    var tipRatioInput by remember { mutableStateOf("") }
    var rounded by remember { mutableStateOf(false) }

    val tipRatio = tipRatioInput.toDoubleOrNull()?:0.0
    val amount = amountInput.toDoubleOrNull()?:0.0
    val  tip = CalculateTip(amount,tipRatio,rounded)
/*
val amout = amountInput.toDoubleOrNull()?:0.0:
Bu satır, amountInput değişkenindeki değeri bir Double değerine dönüştürür.
 Ancak, eğer dönüşüm başarısız olursa (toDoubleOrNull() null döndürürse),
  yerine 0.0 atanır
 */



    //3
    Column(modifier = Modifier
        .safeDrawingPadding()
        .statusBarsPadding()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 40.dp)
        ,horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = stringResource(id = R.string.calculate_tip), fontWeight = FontWeight.Bold, fontSize = 28.sp,modifier = Modifier
            .padding(bottom = 16.dp, top = 40.dp)
            .align(alignment = Alignment.CenterHorizontally))

        //4
        EditNumberField(
            label = R.string.bill_amount,//Bill Amount
            iconImage = R.drawable.money,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, //numara klavyesinin açılmasını sağlar
                imeAction = ImeAction.Next // entera basınca sonraki textfielde geçer
            ),
            value =amountInput,
            onValueChanged = {amountInput=it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxSize()
        )

        //5
        EditNumberField(
            label = R.string.how_was_the_service,//Tip Percentage
            iconImage = R.drawable.percent,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done // entere basınca biter
            ),
            value =tipRatioInput,
            onValueChanged = {tipRatioInput=it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxSize()
        )

        //7
        RoundedRow(rounded = rounded, onRoundedChanged = {rounded=it},modifier = Modifier.padding(bottom = 10.dp))
        Text(text = stringResource(id = R.string.tip_amount,tip),
            style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(150.dp))
    }

}




//6
@Composable
fun RoundedRow(rounded:Boolean, onRoundedChanged:(Boolean)->Unit,modifier :Modifier=Modifier){
    Row(modifier = modifier
        .fillMaxWidth()
        .size(48.dp), verticalAlignment = Alignment.CenterVertically){
        Text(text= stringResource(id = R.string.round_up_tip))
        Switch(checked = rounded, onCheckedChange = onRoundedChanged,modifier= modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End))
    }

}




//3

@Composable
fun EditNumberField(
    @StringRes label : Int,
    @DrawableRes iconImage :Int,
    keyboardOptions: KeyboardOptions,
    value : String,
    onValueChanged: (String) ->Unit,
    modifier: Modifier= Modifier
){
    TextField(value = value,
        onValueChange =onValueChanged,
        singleLine = true,
        leadingIcon= { Icon(painter = painterResource(id = iconImage),null) },
        modifier = modifier,
        label = {Text(stringResource(id = label))},
        keyboardOptions=keyboardOptions)

}




//2

//string bir ifade döndürecek
private fun CalculateTip(amount:Double,tipRatio:Double=15.0,rounded:Boolean):String{
    var tip = tipRatio/100*amount
    if(rounded){
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

````
