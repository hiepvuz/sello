package com.example.sello.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sello.R
import com.example.sello.adapter.CartAdapter
import com.example.sello.databinding.FragmentCartBinding
import com.example.sello.entity.Person
import com.example.sello.viewmodel.CartViewModel
import com.example.sello.viewmodel.ProductViewModel


class CartFragment : Fragment(R.layout.fragment_cart), CartAdapter.OnClickDelete,
    CartAdapter.OnClickPlus, CartAdapter.OnClickMinus,
    CartAdapter.OnClickCheckOut {

    private val cartViewModel: CartViewModel by viewModels()

    private val productViewModel: ProductViewModel by viewModels()

    private var _binding: FragmentCartBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
    }

    private fun initView() {
        val person = arguments?.getParcelable<Person>("person")
        val adapter = CartAdapter(Application(), this, this, this, this)

        binding.rvCart.layoutManager = LinearLayoutManager(context)
        binding.rvCart.adapter = adapter
        if (person != null) {
            cartViewModel.getAllCart(person.personId).observe(viewLifecycleOwner, Observer {
                adapter.setProducts(it)
            })
            var totalPrice = 0.toLong()
            val listCheckoutCart = cartViewModel.searchCart(person.personId)
            for (item in listCheckoutCart) {
                val product = productViewModel.findProductByID(item.productID)
                totalPrice += product.price * item.quantity
                binding.tvCartTotal.text = totalPrice.toString()
            }
            if (listCheckoutCart.isNullOrEmpty()) {
                binding.tvCartTotal.text = "0"
            }
        }
    }

    override fun onClickDelete(personID: String, productID: String) {
        cartViewModel.deleteCart(personID, productID)
        var totalPrice = 0.toLong()
        val listCheckoutCart = cartViewModel.searchCart(personID)
        for (item in listCheckoutCart) {
            val product = productViewModel.findProductByID(item.productID)
            totalPrice += product.price * item.quantity
            binding.tvCartTotal.text = totalPrice.toString()
        }
        if (listCheckoutCart.isNullOrEmpty()) {
            binding.tvCartTotal.text = "0"
        }
    }

    override fun onClickPlus(quantity: Int, personID: String, productID: String) {
        //always
        cartViewModel.updateCartNumber(quantity, personID, productID)
        var totalPrice = 0.toLong()
        val listCheckoutCart = cartViewModel.searchCart(personID)
        for (item in listCheckoutCart) {
            val product = productViewModel.findProductByID(item.productID)
            totalPrice += product.price * item.quantity
            binding.tvCartTotal.text = totalPrice.toString()
        }
    }

    override fun onClickMinus(quantity: Int, personID: String, productID: String) {
        //always
        cartViewModel.updateCartNumber(quantity, personID, productID)
        var totalPrice = 0.toLong()
        val listCheckoutCart = cartViewModel.searchCart(personID)
        for (item in listCheckoutCart) {
            val product = productViewModel.findProductByID(item.productID)
            totalPrice += product.price * item.quantity
            binding.tvCartTotal.text = totalPrice.toString()
        }

    }

    //checkbox
    override fun onClickCheckOut(personID: String, productID: String) {
        //always
        cartViewModel.updateCartCheckout(personID, productID)
        var totalPrice = 0.toLong()
        val listCheckoutCart = cartViewModel.searchCart(personID)
        for (item in listCheckoutCart) {
            val product = productViewModel.findProductByID(item.productID)
            totalPrice += product.price * item.quantity
            binding.tvCartTotal.text = totalPrice.toString()
        }
        if (listCheckoutCart.isNullOrEmpty()) {
            binding.tvCartTotal.text = "0"
        }
    }

    private fun initAction() {
        //an mua hang
        binding.tvCartPayment.setOnClickListener {
            val person = arguments?.getParcelable<Person>("person")
            if (person != null && binding.tvCartTotal.text.toString().toLong() != 0.toLong()) {
                val action =
                    CartFragmentDirections.actionCartFragmentToOrderFragment(
                        person = person
                    )
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Nothing to order", Toast.LENGTH_SHORT).show()
            }
        }
    }


}