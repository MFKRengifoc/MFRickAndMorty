package com.manoffocus.mfrickandmorty.components.mfform

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly


interface MFUserFormInputFilterIF{
    fun validate(value: String): Map<String, String>
}

data class MFUserFormInputData(
    var label: String,
    var stateValue : MutableState<String>,
    var type: KeyboardType,
    var weight: Float,
    var showTrailingIcon: Boolean = true,
    var filters: Array<MFUserFormInputFilter> = emptyArray(),
    var onValueChanges: (String, String) -> Unit,
    var onErrors: (Map<Int, List<Map<String, String>>>) -> Unit
)

sealed class MFUserFormInputFilter(): MFUserFormInputFilterIF{
    class Range(var min: Int, var max: Int): MFUserFormInputFilter(){
        override fun validate(value: String): Map<String, String> {
            if (value.trim().isEmpty()) return mapOf(Pair("notARange", value))
            if (!value.isDigitsOnly()) return mapOf(Pair("notARange", value))
            val result = when{
                value.toInt() < min -> mapOf(Pair("minRange", "$min"))
                value.toInt() > max -> mapOf(Pair("maxRange", "$max"))
                else -> mapOf()
            }
            return result
        }
    }
    class LengthMax(var size: Int): MFUserFormInputFilter(){
        override fun validate(value: String): Map<String, String> {
            val result = when{
                value.length > size -> mapOf(Pair("lengthMax", "$size"))
                else -> mapOf()
            }
            return result
        }
    }
    class LengthMin(var size: Int): MFUserFormInputFilter(){
        override fun validate(value: String): Map<String, String> {
            val result = when{
                value.length < size -> mapOf(Pair("lengthMin", "$size"))
                else -> mapOf()
            }
            return result
        }
    }
    class DigitsOnly(): MFUserFormInputFilter(){
        override fun validate(value: String): Map<String, String> {
            return if (!value.trim().isDigitsOnly()) mapOf(Pair("digitsOnly", value))
            else mapOf()
        }
    }
    class LettersOnly(): MFUserFormInputFilter(){
        override fun validate(value: String): Map<String, String> {
            val pattern = Regex("^[a-zA-Z|\\s]+\$")
            return if (!pattern.matches(value)) mapOf(Pair("lettersOnly", value))
            else mapOf()
        }
    }
    class Required(): MFUserFormInputFilter(){
        override fun validate(value: String): Map<String, String> {
            return if (value.trim().isEmpty()) mapOf(Pair("empty", value))
            else mapOf()
        }
    }
}

private fun filterErrors(value: String, filters: Array<MFUserFormInputFilter>): List<Map<String, String>>{
    val errors = ArrayList<Map<String, String>>()
    filters.map { filter ->
        val res = filter.validate(value)
        if (res.isNotEmpty()) errors.add(res)
    }
    return errors
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MFUserForm(
    modifier: Modifier = Modifier,
    inputs : Array<MFUserFormInputData>,
    onValidForm: (Boolean) -> Unit
){
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 10.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        val inputsMap = remember { mutableStateMapOf<Int, List<Map<String, String>>>() }
        val validValues = remember { mutableStateOf(Array(size = inputs.size, init = {false})) }
        for (index in inputs.indices){
            if (!inputsMap.contains(index)) inputsMap[index] = emptyList()
            MFInput(
                modifier = Modifier
                    .weight(weight = inputs[index].weight)
                    .height(95.dp),
                valueState = inputs[index].stateValue,
                label = inputs[index].label,
                type = inputs[index].type,
                showTrailingIcon = inputs[index].showTrailingIcon,
                validFilters = inputsMap[index],
                imeAction = if (index == inputs.size - 1) ImeAction.Done else ImeAction.Next,
                onValueChange = { new, prev ->
                    val errors = filterErrors(new, filters = inputs[index].filters)
                    if (errors.isNotEmpty()){
                        inputsMap[index] = errors
                        validValues.value[index] = false
                    } else {
                        inputsMap.remove(index)
                        validValues.value[index] = true
                    }
                    inputs[index].onValueChanges(new, prev)
                    inputs[index].onErrors.invoke(inputsMap)
                    val valid = inputsMap.values.stream().allMatch { list ->
                        list.isEmpty() && validValues.value.all { it }
                    }
                    if (!valid){
                        onValidForm.invoke(valid)
                    }
                }
            ){ validText ->
                if (index == inputs.size - 1) {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    val valid = inputsMap.values.stream().allMatch { list ->
                        list.isEmpty() && validValues.value.all { it }
                    }
                    onValidForm.invoke(valid)
                } else {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            }
        }
    }
}