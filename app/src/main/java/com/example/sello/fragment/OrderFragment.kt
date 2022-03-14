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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sello.R
import com.example.sello.adapter.OrderAdapter
import com.example.sello.databinding.FragmentOrderBinding
import com.example.sello.entity.*
import com.example.sello.viewmodel.CartViewModel
import com.example.sello.viewmodel.OrderViewModel
import com.example.sello.viewmodel.ProductViewModel
import com.google.type.Date

class OrderFragment : Fragment(R.layout.fragment_order) {

    private var _binding: FragmentOrderBinding? = null
    private val binding
        get() = _binding!!

    private val orderViewModel: OrderViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
    }

    private fun initView() {
        val person = arguments?.getParcelable<Person>("person")
            if (person != null) {
                val adapter = OrderAdapter(Application())
                binding.rvOrder.layoutManager = LinearLayoutManager(context)
                binding.rvOrder.adapter = adapter
                cartViewModel.searchListCartCheckout(person.personId)
                    .observe(viewLifecycleOwner, Observer {
                        adapter.setOrders(it)
                    })
                binding.edtOrderName.setText(person.name)
                binding.edtOrderAddress.setText(person.address)
                binding.edtOrderPhone.setText(person.phone)
                var totalPrice = 0.toLong()
                val listCheckoutCart = cartViewModel.searchCart(person.personId)
                for (item in listCheckoutCart) {
                    val product = productViewModel.findProductByID(item.productID)
                    totalPrice += product.price * item.quantity
                    binding.tvOrderTotal.text = totalPrice.toString()
                }
            }
    }

    private fun initAction() {
        //mua hang han
        binding.tvOrderPayment.setOnClickListener {
            val person = arguments?.getParcelable<Person>("person")
            if (person != null && binding.tvOrderTotal.text.toString().toLong() != 0.toLong()) {

                //sua ngay de lau id dep
                val timeIdOrder =person.personId + "-" + Date.getDefaultInstance()

                val order = Order(person.personId, person.personId, OrderStatus.Pending)
                orderViewModel.insertOrder(order)
                //list cart chua cac card - id cua cac order
                val listCheckoutCart = cartViewModel.searchCart(person.personId)
                for (item in listCheckoutCart) {
                    if (item.checkout) {
                        val product = productViewModel.findProductByID(item.productID)

                        val personID = item.personID
                        val productID = item.productID
                        val quantity = item.quantity
                        val totalPrice = product.price * quantity
                        val newStock = product.stock - quantity
                        val orderItem =
                            OrderItem(personID, productID, quantity, totalPrice, product.discount)
                        orderViewModel.insertOrderItem(orderItem)
                        orderViewModel.addOrderItemToFireStore(orderItem,timeIdOrder)
                        cartViewModel.checkoutCart(personID)
                        productViewModel.updateStock(newStock,item.productID)
                        productViewModel.updateProductFireStore(product,newStock)
                    }
                }
                binding.tvOrderTotal.text = "0"
                Toast.makeText(context, "Order success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Nothing to order", Toast.LENGTH_SHORT).show()
            }

        }
    }

}